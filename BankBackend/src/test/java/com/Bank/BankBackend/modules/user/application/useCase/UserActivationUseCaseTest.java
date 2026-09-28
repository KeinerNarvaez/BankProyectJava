package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.UserActivationTokenRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserActivationResponse;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserActivationToken;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserActivationTokenRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserActivationUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserActivationTokenRepository userActivationTokenRepository;

    @InjectMocks
    private UserActivationUseCase userActivationUseCase;

    @Test
    void shouldActivateUserSuccessfully() {

        // ARRANGE
        UserActivationTokenRequest request =
                UserActivationTokenRequest.builder()
                        .email("USER@GMAIL.COM")
                        .code("ABC123")
                        .build();

        User user = User.builder()
                .userId(1)
                .email("user@gmail.com")
                .userStatus(UserStatus.INACTIVE)
                .build();

        UserActivationToken activationToken =
                UserActivationToken.builder()
                        .userId(user)
                        .token("ABC123")
                        .used(false)
                        .expire(LocalDateTime.now().plusMinutes(10))
                        .build();

        when(userRepository.findByEmail("user@gmail.com"))
                .thenReturn(Optional.of(user));

        when(userActivationTokenRepository
                .findByUserId_UserIdAndToken(1, "ABC123"))
                .thenReturn(Optional.of(activationToken));

        // ACT
        UserActivationResponse response =
                userActivationUseCase.userActivation(request);

        // ASSERT
        assertNotNull(response);
        assertTrue(response.isValid());
        assertEquals(
                "Se activo correctamente la cuenta",
                response.getMessage()
        );

        verify(userRepository)
                .findByEmail("user@gmail.com");

        verify(userActivationTokenRepository)
                .findByUserId_UserIdAndToken(1, "ABC123");
    }
}