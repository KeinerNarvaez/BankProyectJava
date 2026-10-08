package com.Bank.BankBackend.modules.transaction.domain.repository;

import com.Bank.BankBackend.modules.transaction.domain.model.Transaction;
import org.mapstruct.Mapper;

import java.util.Optional;


public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Integer transactionId);
}
