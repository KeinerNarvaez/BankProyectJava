package com.Bank.BankBackend.modules.gmf.domain.repository;

import com.Bank.BankBackend.modules.gmf.domain.model.GmfHistory;

import java.util.List;

public interface GmfHistoryRepository {
    List<GmfHistory> findPendingChargesByAccountId(Integer accountId);

    GmfHistory save(GmfHistory updatedHistory);
}
