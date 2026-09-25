package com.Bank.BankBackend.modules.user.application.useCase;

import com.Bank.BankBackend.modules.user.application.dto.request.GenerateTokenRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.LoginRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.LoginResponse;
import com.Bank.BankBackend.modules.user.application.port.in.LoginPort;
import com.Bank.BankBackend.modules.user.application.port.out.TokenGeneratorPort;
import com.Bank.BankBackend.modules.user.domain.exception.InvalidCredentialsException;
import com.Bank.BankBackend.modules.user.domain.exception.UserEmailNotFoundException;
import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements LoginPort {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGeneratorPort tokenGeneratorPort;
    
    @Override
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().toLowerCase();
        User user = userRepository.findByEmailAndUserStatusActive(email)
                .orElseThrow(() -> new UserEmailNotFoundException(email));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        GenerateTokenRequest tokenRequest = GenerateTokenRequest.builder()
                .userId(user.getUserId())
                .rolType(user.getRolType())
                .build();

        String token =tokenGeneratorPort.generateAccessToken(tokenRequest);

        return LoginResponse.builder()
                .token("Token: " + token)
                .build();
    }
}
