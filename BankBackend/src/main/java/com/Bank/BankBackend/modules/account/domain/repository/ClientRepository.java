package com.Bank.BankBackend.modules.account.domain.repository;

import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(Integer clientId);

    Optional<Client> findByIdentifyNumber(Integer identifyNumber);

    Optional<Client> findByEmail(String email);

    List<Client> findByStatus(TypeStatus typeStatus);
}
