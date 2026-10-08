package com.Bank.BankBackend.modules.account.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.account.application.mapper.ClientMapper;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.domain.repository.ClientRepository;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client save(Client client) {

        ClientEntity entity = clientMapper.toEntity(client);

        ClientEntity savedEntity = clientJpaRepository.save(entity);

        return clientMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Client> findById(Integer clientId) {

        return clientJpaRepository.findById(clientId)
                .map(clientMapper::toDomain);
    }

    @Override
    public Optional<Client> findByIdentifyNumber(Integer identifyNumber) {

        return clientJpaRepository
                .findByIdentifyNumber(identifyNumber)
                .map(clientMapper::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {

        return clientJpaRepository
                .findByEmail(email)
                .map(clientMapper::toDomain);
    }

    @Override
    public List<Client> findByStatus(TypeStatus typeStatus) {

        return clientJpaRepository
                .findByStatus(typeStatus)
                .stream()
                .map(clientMapper::toDomain)
                .toList();
    }
}