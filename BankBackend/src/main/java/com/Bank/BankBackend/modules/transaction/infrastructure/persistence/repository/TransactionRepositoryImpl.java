package com.Bank.BankBackend.modules.transaction.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.transaction.application.mapper.TransactionMapper;
import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import com.Bank.BankBackend.modules.transaction.domain.repository.TransactionRepository;
import com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl
        implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction save(Transaction transaction) {

        TransactionEntity entity =
                transactionMapper.toEntity(transaction);

        TransactionEntity savedEntity =
                transactionJpaRepository.save(entity);

        return transactionMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(Integer transactionId) {

        return transactionJpaRepository
                .findById(transactionId)
                .map(transactionMapper::toDomain);
    }
}