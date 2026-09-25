package com.Bank.BankBackend.modules.user.application.mapper;

import com.Bank.BankBackend.modules.user.domain.model.UserActivationToken;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.UserActivationTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserActivationTokenMapper {
    UserActivationToken toDomain(UserActivationTokenEntity entity);
    UserActivationTokenEntity toEntity(UserActivationToken domain);
}
