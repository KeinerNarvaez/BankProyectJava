package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.LoginRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.LoginResponse;
import com.Bank.BankBackend.modules.user.application.port.out.TokenGeneratorPort;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenGeneratorPort tokenGeneratorPort;

    @InjectMocks
    private LoginUseCase loginUseCase;


    @Test
    void shouldReturnLogin(){

        User user = User.builder()
                .userId(1)
                .email("user@gmail.com")
                .password("$2a$10$liN8wPdJ/RUKmfi1On.tFu1NCro1CXgFc7QmiFhfSyDfDDVh4O3Fq")
                .userStatus(UserStatus.ACTIVE)
                .build();

        LoginRequest loginRequest = LoginRequest.builder()
                .email("user@gmail.com")
                .password("Katsof26**")
                .build();

        when(userRepository.findByEmailAndUserStatusActive(
                user.getEmail()
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        )).thenReturn(true);

        when(tokenGeneratorPort.generateAccessToken(any()))
                .thenReturn("fake-jwt-token");

        LoginResponse response =
                loginUseCase.login(loginRequest);

        assertNotNull(response);

        verify(userRepository)
                .findByEmailAndUserStatusActive("user@gmail.com");
    }
}
