package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.CreateUserRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserResponse;
import com.Bank.BankBackend.modules.user.application.port.out.EmailNotificationPort;
import com.Bank.BankBackend.modules.user.domain.model.RolType;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserActivationTokenRepository;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailNotificationPort emailNotificationPort;

    @Mock
    private UserActivationTokenRepository userActivationTokenRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void shouldCreateUserSuccessfully() {

        // ARRANGE
        CreateUserRequest request = CreateUserRequest.builder()
                .email("USER@GMAIL.COM")
                .password("Password123**")
                .rolType(RolType.ADVISOR)
                .build();

        when(userRepository.findByEmailAndUserStatusActive("user@gmail.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("Password123**"))
                .thenReturn("encoded-password");

        User savedUser = User.builder()
                .userId(1)
                .email("user@gmail.com")
                .password("encoded-password")
                .userStatus(UserStatus.INACTIVE)
                .rolType(RolType.ADVISOR)
                .build();

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        // ACT
        UserResponse response =
                createUserUseCase.create(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(1, response.getUserId());
        assertEquals("user@gmail.com", response.getEmail());
        assertEquals(UserStatus.INACTIVE, response.getUserStatus());
        assertEquals(RolType.ADVISOR, response.getRolType());

        verify(userRepository)
                .findByEmailAndUserStatusActive("user@gmail.com");

        verify(passwordEncoder)
                .encode("Password123**");

        verify(userActivationTokenRepository)
                .save(any());

        verify(emailNotificationPort)
                .sendActivationEmail(
                        org.mockito.ArgumentMatchers.eq("user@gmail.com"),
                        org.mockito.ArgumentMatchers.anyString()
                );
    }
}