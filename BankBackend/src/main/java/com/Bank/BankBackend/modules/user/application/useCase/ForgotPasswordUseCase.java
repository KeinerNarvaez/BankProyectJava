package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.ChangeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.CreatePasswordResetTokenRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.ForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.VerifyCodeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.VerifyCodeForgotPasswordResponse;
import com.Bank.BankBackend.modules.user.application.port.in.ForgotPasswordPort;
import com.Bank.BankBackend.modules.user.application.port.out.EmailNotificationPort;
import com.Bank.BankBackend.modules.user.domain.exception.*;
import com.Bank.BankBackend.modules.user.domain.model.HistoryPassword;
import com.Bank.BankBackend.modules.user.domain.model.PasswordResetToken;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.repository.HistoryPasswordRepository;
import com.Bank.BankBackend.modules.user.domain.repository.PasswordResetTokenRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ForgotPasswordUseCase implements ForgotPasswordPort {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final HistoryPasswordRepository historyPasswordRepository;
    private final EmailNotificationPort emailNotificationPort;


    public void saveOrUpdate(CreatePasswordResetTokenRequest request) {
        passwordResetTokenRepository.save(
                PasswordResetToken.builder()
                        .passwordResetTokenId(request.getPasswordResetTokenId())
                        .userId(request.getUserId())
                        .token(request.getToken())
                        .expire(request.getExpire())
                        .used(request.isUsed())
                        .createdAt(request.getCreatedAt())
                        .build()
        );
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserEmailNotFoundException(request.getEmail()));
        String token= UUID.randomUUID().toString().replace("-", "").substring(0, 6);// <- un valor de 0 a 6 el numero de caracteres
        saveOrUpdate(CreatePasswordResetTokenRequest.builder()
                .userId(user)
                .token(token)
                .expire(LocalDateTime.now().plusMinutes(15)) //15 minutos para que el usuario ingrese el código y cambien contraseña
                .used(false)
                .createdAt(LocalDateTime.now())
                .build());

        emailNotificationPort.sendPasswordRecoveryEmail(user.getEmail(), token);
    }

    private void validateToken(PasswordResetToken token) {
        if (token.getExpire().isBefore(LocalDateTime.now())) {
            throw new CodeNotValidException();
        }
        if (token.isUsed()) {
            throw new CodeUsedException();
        }
    }

    @Override
    public VerifyCodeForgotPasswordResponse verifyCode(VerifyCodeForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserEmailNotFoundException(request.getEmail()));

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserIdAndToken(user.getUserId(), request.getVerificationCode())
                .orElseThrow(CodeOrUserNotValidException::new);

        validateToken(passwordResetToken);

        return VerifyCodeForgotPasswordResponse.builder()
                .valid(true)
                .message("Código válido")
                .build();

    }

    @Override
    @Transactional
    public void changePassword(ChangeForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserEmailNotFoundException(request.getEmail()));

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserIdAndToken(user.getUserId(), request.getToken())
                .orElseThrow(CodeOrUserNotValidException::new);

        validateToken(passwordResetToken);

        boolean isReusedPassword =
                historyPasswordRepository.findByUserId(user.getUserId())
                        .stream()
                        .anyMatch(history -> passwordEncoder.matches(request.getNewPassword(), history.getPassword()));

        if (isReusedPassword) {
            throw new PasswordAlreadyUsedException();
        }
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        HistoryPassword historyPassword = HistoryPassword.builder()
                .userId(user)
                .password(user.getPassword())
                .createdAt(LocalDateTime.now())
                .build();

        User updatedUser = user.toBuilder()
                .password(encodedPassword)
                .updatedAt(LocalDateTime.now())
                .build();

        PasswordResetToken updatePasswordResetToken= passwordResetToken.toBuilder()
                .used(true)
                .build();

        historyPasswordRepository.save(historyPassword);
        userRepository.save(updatedUser);
        passwordResetTokenRepository.save(updatePasswordResetToken);

    }


}
