package com.Bank.BankBackend.modules.transaction.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository
        extends JpaRepository<TransactionEntity, Integer> {
}