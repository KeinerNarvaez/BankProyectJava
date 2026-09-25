package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.application.mapper.UserActivationTokenMapper;
import com.Bank.BankBackend.modules.user.domain.model.UserActivationToken;
import com.Bank.BankBackend.modules.user.domain.repository.UserActivationTokenRepository;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.UserActivationTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserActivationTokenRepositoryImpl implements UserActivationTokenRepository {
    private final UserActivationTokenJpaRepository userActivationTokenJpaRepository;
    private final UserActivationTokenMapper userActivationTokenMapper;

    @Override
    public UserActivationToken save(UserActivationToken userActivationToken) {
        UserActivationTokenEntity entity = userActivationTokenMapper.toEntity(userActivationToken);
        UserActivationTokenEntity savedEntity = userActivationTokenJpaRepository.save(entity);
        return userActivationTokenMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<UserActivationToken> findByUserId_UserIdAndToken(Integer userId, String token) {
        return userActivationTokenJpaRepository.findByUserId_UserIdAndToken(userId,token)
            .map(userActivationTokenMapper::toDomain);
    }
}
