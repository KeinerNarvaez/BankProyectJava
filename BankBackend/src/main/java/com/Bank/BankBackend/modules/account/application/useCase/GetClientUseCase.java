package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.GetClientRequest;
import com.Bank.BankBackend.modules.account.application.dto.response.GetClientResponse;
import com.Bank.BankBackend.modules.account.application.dto.response.GetListClientResponse;
import com.Bank.BankBackend.modules.account.application.port.in.GetClientPort;
import com.Bank.BankBackend.modules.account.domain.exception.ClientNotFoundException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientsNotExistException;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetClientUseCase implements GetClientPort {

    private final ClientRepository clientRepository;

    @Override
    public List<GetListClientResponse> getClientInactive() {
        List<Client> list = clientRepository.findByStatus(TypeStatus.INACTIVE);
        if(list.isEmpty()){
            throw new ClientsNotExistException();
        }
        return list.stream()
                .map(client -> GetListClientResponse.builder()
                        .clientId(client.getClientId())
                        .names(client.getNames())
                        .lastNames(client.getLastNames())
                        .identifyNumber(client.getIdentifyNumber())
                        .email(client.getEmail())
                        .build()
                )
                .toList();
    }

    @Override
    public List<GetListClientResponse> getClientActive() {
        List<Client> list = clientRepository.findByStatus(TypeStatus.ACTIVE);
        if(list.isEmpty()){
            throw new ClientsNotExistException();
        }
        return list.stream()
                .map(client -> GetListClientResponse.builder()
                        .clientId(client.getClientId())
                        .names(client.getNames())
                        .lastNames(client.getLastNames())
                        .identifyNumber(client.getIdentifyNumber())
                        .email(client.getEmail())
                        .build()
                )
                .toList();
    }

    @Override
    public GetClientResponse getClient(GetClientRequest request) {

        Client client = clientRepository
                .findByIdentifyNumber(request.getIdentifyNumber())
                .orElseThrow(() ->
                        new ClientNotFoundException(request.getIdentifyNumber())
                );

        return GetClientResponse.builder()
                .clientId(client.getClientId())
                .names(client.getNames())
                .lastNames(client.getLastNames())
                .identifyNumber(client.getIdentifyNumber())
                .email(client.getEmail())
                .birthday(client.getBirthday())
                .status(client.getStatus())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .typeIdentify(client.getTypeIdentify())
                .userCreation(client.getUserCreation())
                .build();
    }
}
