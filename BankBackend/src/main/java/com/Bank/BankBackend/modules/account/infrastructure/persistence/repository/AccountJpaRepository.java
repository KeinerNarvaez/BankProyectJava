package com.Bank.BankBackend.modules.account.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountJpaRepository
        extends JpaRepository<AccountEntity, Integer> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByClientId_ClientIdAndAccountStatus(Integer clientId, AccountStatus accountStatus);

    List<AccountEntity> findAllByClientId_ClientId(Integer clientId);
}