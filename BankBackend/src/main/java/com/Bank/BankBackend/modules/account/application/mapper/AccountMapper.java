package com.Bank.BankBackend.modules.account.application.mapper;

import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.AccountEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ClientMapper.class})
public interface AccountMapper {
    Account toDomain(AccountEntity entity);
    AccountEntity toEntity(Account domain);
}
