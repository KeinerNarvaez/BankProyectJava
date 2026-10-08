package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DepositRequest;
import com.Bank.BankBackend.modules.account.application.port.in.DepositPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.transaction.application.port.in.RegisterDepositTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepositUseCase implements DepositPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final RegisterDepositTransactionPort registerDepositTransactionPort;

    @Transactional
    @Override
    public void deposit(Integer userId,DepositRequest request) {

        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(request.getIdentifyNumber())
                );

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(request.getAccountNumber())
                );

        if (account.getAccountStatus() == AccountStatus.CANCELED) {
            throw new AccountCanceledException();
        }

        if (account.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new AccountInactiveException();
        }
        if (!account.getClientId().getClientId().equals(client.getClientId())) {
            throw new AccountDoesNotBelongToClientException();
        }

        BigDecimal newBalance = account.getBalance()
                .add(request.getAmount());

        BigDecimal newAvailableBalance = account.getAvailableBalance()
                .add(request.getAmount());

        Account updateAccount = account.toBuilder()
                .balance(newBalance)
                .availableBalance(newAvailableBalance)
                .updatedAt(LocalDateTime.now())
                .build();

        registerDepositTransactionPort.register(updateAccount.getAccountId(), request.getAmount(), userId);
        accountRepository.save(updateAccount);
    }
}
