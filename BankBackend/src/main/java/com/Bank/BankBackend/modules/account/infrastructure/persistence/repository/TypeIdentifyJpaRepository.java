package com.Bank.BankBackend.modules.account.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.TypeIdentifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeIdentifyJpaRepository extends JpaRepository<TypeIdentifyEntity,Integer> {

    Optional<TypeIdentifyEntity> findByName(String name);
}
