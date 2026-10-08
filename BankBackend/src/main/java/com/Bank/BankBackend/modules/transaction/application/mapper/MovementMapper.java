package com.Bank.BankBackend.modules.transaction.application.mapper;

import com.Bank.BankBackend.modules.transaction.domain.model.Movement;
import com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity.MovementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface MovementMapper {
    Movement toDomain(MovementEntity entity);
    MovementEntity toEntity(Movement domain);
}
