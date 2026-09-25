package com.Bank.BankBackend.modules.user.application.mapper;

import com.Bank.BankBackend.modules.user.domain.model.User;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity entity);
    UserEntity toEntity(User domain);
}
