package com.Bank.BankBackend.modules.user.application.mapper;

import com.Bank.BankBackend.modules.user.domain.model.PasswordResetToken;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.PasswordResetTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PasswordResetTokenMapper {
    PasswordResetToken toDomain(PasswordResetTokenEntity entity);
    PasswordResetTokenEntity toEntity(PasswordResetToken domain);
}
