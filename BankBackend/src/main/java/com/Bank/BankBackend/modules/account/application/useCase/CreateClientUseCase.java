package com.Bank.BankBackend.modules.account.application.useCase;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateClientRequest;
import com.Bank.BankBackend.modules.account.application.port.in.CreateClientPort;
import com.Bank.BankBackend.modules.account.domain.exception.ClientEmailAlreadyExistsException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientIdentifyNumberAlreadyExistsException;
import com.Bank.BankBackend.modules.account.domain.exception.ClientUnderageException;
import com.Bank.BankBackend.modules.account.domain.exception.TypeIdentifyNotFoundException;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class CreateClientUseCase implements CreateClientPort {

    private final ClientRepository clientRepository;
    private final TypeIdentifyRepository typeIdentifyRepository;

    @Transactional
    @Override
    public void create(Integer userId,CreateClientRequest request) {

        Integer identifyNumber= request.getIdentifyNumber();
        String email = request.getEmail();

        if (Period.between(request.getBirthday(), LocalDate.now()).getYears() < 18) {
            throw new ClientUnderageException();
        }

        if (clientRepository.findByIdentifyNumber(identifyNumber).isPresent()){
            throw new ClientIdentifyNumberAlreadyExistsException(identifyNumber);
        }

        if (clientRepository.findByEmail(email).isPresent()){
            throw new ClientEmailAlreadyExistsException(email);
        }

        TypeIdentify typeIdentify=
                typeIdentifyRepository.findByName(request.getTypeIdentifyName())
                .orElseThrow(TypeIdentifyNotFoundException::new);

        Client client = Client.builder()
                .typeIdentify(typeIdentify)
                .names(request.getNames())
                .lastNames(request.getLastNames())
                .identifyNumber(request.getIdentifyNumber())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .status(TypeStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userCreation(userId)
                .build();

        clientRepository.save(client);
    }
}
