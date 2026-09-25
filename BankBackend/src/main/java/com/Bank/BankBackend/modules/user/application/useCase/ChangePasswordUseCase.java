package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.ChangePasswordRequest;
import com.Bank.BankBackend.modules.user.application.port.in.ChangePasswordPort;
import com.Bank.BankBackend.modules.user.application.port.out.TokenGeneratorPort;
import com.Bank.BankBackend.modules.user.domain.exception.InvalidNewPasswordException;
import com.Bank.BankBackend.modules.user.domain.exception.UserIdNotFoundException;
import com.Bank.BankBackend.modules.user.domain.exception.PasswordAlreadyUsedException;
import com.Bank.BankBackend.modules.user.domain.model.HistoryPassword;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.repository.HistoryPasswordRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase implements ChangePasswordPort {
    private final UserRepository userRepository;
    private final HistoryPasswordRepository historyPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changePassword(Integer userId, ChangePasswordRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserIdNotFoundException(userId)
                );

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        )) {
            throw new InvalidNewPasswordException();
        }

        boolean isReusedPassword =
                historyPasswordRepository.findByUserId(user.getUserId())
                        .stream()
                        .anyMatch(history ->
                                passwordEncoder.matches(
                                        request.getNewPassword(),
                                        history.getPassword()
                                )
                        );

        if (isReusedPassword) {
            throw new PasswordAlreadyUsedException();
        }

        HistoryPassword createRegister = HistoryPassword.builder()
                .userId(user)
                .password(user.getPassword())
                .createdAt(LocalDateTime.now())
                .build();

        User updatedUser = User.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(passwordEncoder.encode(request.getNewPassword()))
                .userStatus(user.getUserStatus())
                .rolType(user.getRolType())
                .createdAt(user.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(updatedUser);
        historyPasswordRepository.save(createRegister);
    }

}
