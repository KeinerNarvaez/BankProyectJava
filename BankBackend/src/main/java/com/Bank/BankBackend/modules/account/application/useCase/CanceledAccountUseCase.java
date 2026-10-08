package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CancelAccountRequest;
import com.Bank.BankBackend.modules.account.application.port.in.CanceledAccountPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CanceledAccountUseCase implements CanceledAccountPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void canceledAccount(CancelAccountRequest request) {
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

        if (account.getAccountStatus() == AccountStatus.CANCELED) {
            throw new AccountAlreadyCancelledException();
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountBalanceNotZeroException();
        }

        Account updatedAccount = account.toBuilder()
                .accountStatus(AccountStatus.CANCELED)
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedAccount);

    }
}
