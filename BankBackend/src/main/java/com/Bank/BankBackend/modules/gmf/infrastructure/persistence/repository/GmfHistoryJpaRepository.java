package com.Bank.BankBackend.modules.gmf.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.gmf.domain.model.GmfOperationType;
import com.Bank.BankBackend.modules.gmf.infrastructure.persistence.entity.GmfHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GmfHistoryJpaRepository extends JpaRepository<GmfHistoryEntity, Integer> {
    List<GmfHistoryEntity> findAllByAccountIdAndOperationType(Integer accountId, GmfOperationType operationType);
}
