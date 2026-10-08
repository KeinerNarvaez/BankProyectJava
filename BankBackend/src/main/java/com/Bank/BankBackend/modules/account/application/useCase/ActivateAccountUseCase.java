package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateAccountRequest;
import com.Bank.BankBackend.modules.account.application.port.in.ActivateAccountPort;
import com.Bank.BankBackend.modules.account.domain.exception.AccountAlreadyActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.AccountDoesNotBelongToClientException;
import com.Bank.BankBackend.modules.account.domain.exception.AccountNotFoundException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivateAccountUseCase implements ActivateAccountPort {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Override
    public void activateAccount(ActivateAccountRequest request) {

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

        if (!account.getClientId().getClientId().equals(client.getClientId())) {
            throw new AccountDoesNotBelongToClientException();
        }

        if (account.getAccountStatus() == AccountStatus.ACTIVE) {
            throw new AccountAlreadyActiveException();
        }

        Account updatedAccount = account.toBuilder()
                .accountStatus(AccountStatus.ACTIVE)
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedAccount);
    }
}
