package com.Bank.BankBackend.modules.transaction.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementJpaRepository
        extends JpaRepository<MovementEntity, Integer> {
}