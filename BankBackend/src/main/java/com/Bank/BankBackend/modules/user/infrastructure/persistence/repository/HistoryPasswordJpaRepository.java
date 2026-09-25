package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.HistoryPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryPasswordJpaRepository extends JpaRepository<HistoryPasswordEntity,Integer> {
    List<HistoryPasswordEntity> findByUserId_UserId(Integer userId);
}
