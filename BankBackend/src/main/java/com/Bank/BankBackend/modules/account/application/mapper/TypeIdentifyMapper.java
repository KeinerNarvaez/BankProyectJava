package com.Bank.BankBackend.modules.account.application.mapper;

import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.TypeIdentifyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeIdentifyMapper {
    TypeIdentify toDomain(TypeIdentifyEntity entity);
    TypeIdentifyEntity toEntity(TypeIdentify domain);
}
