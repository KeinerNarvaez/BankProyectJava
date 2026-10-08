package com.Bank.BankBackend.modules.transaction.application.mapper;

import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toDomain(TransactionEntity entity);
    TransactionEntity toEntity(Transaction domain);
}
