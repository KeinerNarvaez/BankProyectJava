package com.Bank.BankBackend.modules.account.domain.repository;

import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(Integer accountId);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByClientId_ClientIdAndAccountStatus(Integer clientId, AccountStatus accountStatus);

    List<Account> findAllByClientId_ClientId(Integer clientId);

}
