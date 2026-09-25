package com.Bank.BankBackend.modules.user.application.port.out;

import com.Bank.BankBackend.modules.user.application.dto.request.GenerateTokenRequest;

import java.util.Optional;

public interface TokenGeneratorPort {

    String generateAccessToken(GenerateTokenRequest request);
    String generateRefreshToken(Integer userId);
    Optional<Integer> extractUserId(String token);
    Optional<String> extractRole(String token);
    boolean isTokenValid(String token);
}
