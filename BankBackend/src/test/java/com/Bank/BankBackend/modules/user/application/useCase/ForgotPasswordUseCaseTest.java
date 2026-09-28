package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.ChangeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.ForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.VerifyCodeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.VerifyCodeForgotPasswordResponse;
import com.Bank.BankBackend.modules.user.application.port.out.EmailNotificationPort;
import com.Bank.BankBackend.modules.user.domain.model.PasswordResetToken;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.repository.HistoryPasswordRepository;
import com.Bank.BankBackend.modules.user.domain.repository.PasswordResetTokenRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordUseCaseTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HistoryPasswordRepository historyPasswordRepository;

    @Mock
    private EmailNotificationPort emailNotificationPort;

    @InjectMocks
    private ForgotPasswordUseCase forgotPasswordUseCase;

    @Test
    void shouldSendPasswordRecoveryEmail() {

        // ARRANGE
        ForgotPasswordRequest request =
                ForgotPasswordRequest.builder()
                        .email("user@gmail.com")
                        .build();

        User user = User.builder()
                .userId(1)
                .email("user@gmail.com")
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail("user@gmail.com"))
                .thenReturn(Optional.of(user));

        // ACT
        forgotPasswordUseCase.forgotPassword(request);

        // ASSERT
        verify(userRepository)
                .findByEmail("user@gmail.com");

        verify(passwordResetTokenRepository)
                .save(any(PasswordResetToken.class));

        verify(emailNotificationPort)
                .sendPasswordRecoveryEmail(
                        eq("user@gmail.com"),
                        anyString()
                );
    }
}