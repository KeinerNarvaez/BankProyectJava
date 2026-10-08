package com.Bank.BankBackend.modules.account.application.mapper;

import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.ClientEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TypeIdentifyMapper.class})
public interface ClientMapper {
    Client toDomain(ClientEntity entity);
    ClientEntity toEntity(Client domain);
}
