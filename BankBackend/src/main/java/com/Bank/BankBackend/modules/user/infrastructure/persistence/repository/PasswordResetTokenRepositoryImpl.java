package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.application.mapper.PasswordResetTokenMapper;
import com.Bank.BankBackend.modules.user.domain.model.PasswordResetToken;
import com.Bank.BankBackend.modules.user.domain.repository.PasswordResetTokenRepository;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.PasswordResetTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepository {
    private final PasswordResetTokenJpaRepository passwordResetTokenJpaRepository;
    private final PasswordResetTokenMapper passwordResetTokenMapper;

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        PasswordResetTokenEntity entity = passwordResetTokenMapper.toEntity(passwordResetToken);
        PasswordResetTokenEntity saveEntity = passwordResetTokenJpaRepository.save(entity);
        return passwordResetTokenMapper.toDomain(saveEntity);
    }

    @Override
    public Optional<PasswordResetToken> findByUserIdAndToken(Integer userId, String token) {
        return passwordResetTokenJpaRepository.findByUserId_UserIdAndToken(userId,token)
                .map(passwordResetTokenMapper::toDomain);
    }
}
