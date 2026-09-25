package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.UserActivationTokenRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserActivationResponse;
import com.Bank.BankBackend.modules.user.application.port.in.UserActivationPort;
import com.Bank.BankBackend.modules.user.domain.exception.CodeNotValidException;
import com.Bank.BankBackend.modules.user.domain.exception.CodeOrUserNotValidException;
import com.Bank.BankBackend.modules.user.domain.exception.UserEmailNotFoundException;

import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserActivationToken;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserActivationTokenRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserActivationUseCase implements UserActivationPort {
    private final UserRepository userRepository;
    private final UserActivationTokenRepository userActivationTokenRepository;

    @Override
    @Transactional
    public UserActivationResponse userActivation(UserActivationTokenRequest request) {
        String email = request.getEmail().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));

        UserActivationToken userActivationToken = userActivationTokenRepository.findByUserId_UserIdAndToken(user.getUserId(), request.getCode())
                .orElseThrow(CodeOrUserNotValidException::new);

        if (userActivationToken.isUsed()) {
            throw new CodeOrUserNotValidException();
        }

        LocalDateTime now = LocalDateTime.now();

        if (userActivationToken.getExpire().isBefore(now)) {
            throw new CodeNotValidException();
        }

        User updateUser = user.toBuilder()
                .userStatus(UserStatus.ACTIVE)
                .updatedAt(now)
                .build();

        UserActivationToken updated = userActivationToken.toBuilder()
                .used(true)
                .build();

        userRepository.save(updateUser);
        userActivationTokenRepository.save(updated);

        return UserActivationResponse.builder()
                .valid(true)
                .message("Se activo correctamente la cuenta")
                .build();
    }
}
