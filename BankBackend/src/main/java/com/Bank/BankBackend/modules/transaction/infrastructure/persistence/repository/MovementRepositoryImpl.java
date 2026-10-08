package com.Bank.BankBackend.modules.transaction.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.transaction.application.mapper.MovementMapper;
import com.Bank.BankBackend.modules.transaction.domain.model.Movement;
import com.Bank.BankBackend.modules.transaction.domain.repository.MovementRepository;
import com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity.MovementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryImpl
        implements MovementRepository {

    private final MovementJpaRepository movementJpaRepository;
    private final MovementMapper movementMapper;

    @Override
    public void save(Movement movement) {

        MovementEntity entity =
                movementMapper.toEntity(movement);

        movementJpaRepository.save(entity);
    }
}