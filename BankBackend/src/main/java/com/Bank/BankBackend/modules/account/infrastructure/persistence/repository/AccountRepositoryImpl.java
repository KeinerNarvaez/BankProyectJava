package com.Bank.BankBackend.modules.account.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.account.application.mapper.AccountMapper;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountMapper accountMapper;
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account save(Account account) {

        AccountEntity entity = accountMapper.toEntity(account);

        AccountEntity savedEntity = accountJpaRepository.save(entity);

        return accountMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Account> findById(Integer accountId) {

        return accountJpaRepository
                .findById(accountId)
                .map(accountMapper::toDomain);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountJpaRepository
                .findByAccountNumber(accountNumber)
                .map(accountMapper::toDomain);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {

        return accountJpaRepository
                .existsByAccountNumber(accountNumber);
    }

    @Override
    public boolean existsByClientId_ClientIdAndAccountStatus(Integer clientId, AccountStatus accountStatus) {
        return accountJpaRepository.existsByClientId_ClientIdAndAccountStatus(clientId, accountStatus);
    }

    @Override
    public List<Account> findAllByClientId_ClientId(Integer clientId) {
        return accountJpaRepository
                .findAllByClientId_ClientId(clientId)
                .stream()
                .map(accountMapper::toDomain)
                .toList();
    }

}