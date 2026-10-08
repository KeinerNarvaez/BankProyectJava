package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateGmfExemptionRequest;
import com.Bank.BankBackend.modules.account.application.port.in.DeactivateGmfExemptionPort;
import com.Bank.BankBackend.modules.account.domain.exception.*;
import com.Bank.BankBackend.modules.account.domain.model.Account;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeactivateGmfExemptionUseCase implements DeactivateGmfExemptionPort {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public void deactivate(DeactivateGmfExemptionRequest request) {

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

        if (account.getGmfExempt() == false) {
            throw new AccountGmfAlreadyInactiveException();
        }

        Account updatedAccount = account.toBuilder()
                .gmfExempt(false)
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepository.save(updatedAccount);
    }
}
