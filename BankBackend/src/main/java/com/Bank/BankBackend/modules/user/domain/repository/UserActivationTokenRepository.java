package com.Bank.BankBackend.modules.user.domain.repository;

import com.Bank.BankBackend.modules.user.domain.model.UserActivationToken;

import java.util.Optional;

public interface UserActivationTokenRepository {
    UserActivationToken save(UserActivationToken userActivationToken);
    Optional<UserActivationToken> findByUserId_UserIdAndToken(Integer userId, String token);

}
