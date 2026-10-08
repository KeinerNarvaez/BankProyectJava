package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateAccountRequest;
import com.Bank.BankBackend.modules.account.application.port.in.DeactivateAccountPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeactivateAccountUseCase implements DeactivateAccountPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void deactivateAccount(DeactivateAccountRequest request) {

        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() -> new ClientNotFoundException(request.getIdentifyNumber()));

        Account account = accountRepository
                .findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(request.getAccountNumber()));

        if (!account.getClientId().getClientId().equals(client.getClientId())) {
            throw new AccountDoesNotBelongToClientException();
        }

        if (account.getAccountStatus() == AccountStatus.CANCELED) {
            throw new AccountAlreadyCancelledException();
        }

        if (account.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new AccountAlreadyInactiveException();
        }

        Account updatedAccount = account.toBuilder()
                .accountStatus(AccountStatus.INACTIVE)
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedAccount);
    }
}
