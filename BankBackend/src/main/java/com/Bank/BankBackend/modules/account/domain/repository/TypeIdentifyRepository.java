package com.Bank.BankBackend.modules.account.domain.repository;

import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;

import java.util.List;
import java.util.Optional;

public interface TypeIdentifyRepository {
    TypeIdentify save(TypeIdentify accountType);

    Optional<TypeIdentify> findByName(String name);

    List<TypeIdentify> findAll();
}
