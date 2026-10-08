package com.Bank.BankBackend.modules.account.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientJpaRepository extends JpaRepository<ClientEntity,Integer> {
    Optional<ClientEntity> findByIdentifyNumber(Integer identifyNumber);

    Optional<ClientEntity> findByEmail(String email);

    List<ClientEntity> findByStatus(TypeStatus status);
}
