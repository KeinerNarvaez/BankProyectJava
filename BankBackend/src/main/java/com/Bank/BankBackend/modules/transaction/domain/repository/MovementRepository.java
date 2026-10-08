package com.Bank.BankBackend.modules.transaction.domain.repository;

import com.Bank.BankBackend.modules.transaction.domain.model.Movement;

public interface MovementRepository {
    void save(Movement movement);
}
