package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateClientRequest;
import com.Bank.BankBackend.modules.account.application.port.in.DeactivateClientPort;
import com.Bank.BankBackend.modules.account.domain.exception.AccountRemainsActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.AccountRepository;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeactivateClientUseCase implements DeactivateClientPort {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public void deactivateClient(DeactivateClientRequest request) {
        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(request.getIdentifyNumber())
                );

        if (accountRepository.existsByClientId_ClientIdAndAccountStatus(client.getClientId(), AccountStatus.ACTIVE)){
                throw new AccountRemainsActiveException();
        }

        Client updateClient =client.toBuilder()
                .status(TypeStatus.INACTIVE)
                .updatedAt(LocalDateTime.now())
                .build();
        clientRepository.save(updateClient);
    }
}
