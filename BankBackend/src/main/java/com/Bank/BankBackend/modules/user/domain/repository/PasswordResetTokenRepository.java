package com.Bank.BankBackend.modules.user.domain.repository;

import com.Bank.BankBackend.modules.user.domain.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository {
    PasswordResetToken save(PasswordResetToken passwordResetToken);
    Optional<PasswordResetToken> findByUserIdAndToken(Integer userId, String token);
}
