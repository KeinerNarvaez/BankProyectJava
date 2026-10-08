package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateClientRequest;
import com.Bank.BankBackend.modules.account.application.port.in.ActivateClientPort;
import com.Bank.BankBackend.modules.account.domain.exception.ClientAlreadyActiveException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class ActivateClientUseCase implements ActivateClientPort {

    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public void activateClient(ActivateClientRequest request) {

        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(request.getIdentifyNumber())
                );

        if (client.getStatus() == TypeStatus.ACTIVE) {
            throw new ClientAlreadyActiveException();
        }

        Client updatedClient = client.toBuilder()
                .status(TypeStatus.ACTIVE)
                .updatedAt(LocalDateTime.now())
                .build();

        clientRepository.save(updatedClient);
    }
}

