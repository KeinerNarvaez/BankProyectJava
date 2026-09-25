package com.Bank.BankBackend.modules.user.domain.repository;

import com.Bank.BankBackend.modules.user.domain.model.HistoryPassword;

import java.util.List;
import java.util.Optional;

public interface HistoryPasswordRepository {
    HistoryPassword save(HistoryPassword historyPassword);
    Optional<HistoryPassword> findById(Integer historyPasswordId);
    List<HistoryPassword> findByUserId(Integer userId);
}
