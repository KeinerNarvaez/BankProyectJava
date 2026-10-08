package com.Bank.BankBackend.modules.account.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.account.application.mapper.TypeIdentifyMapper;
import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.repository.TypeIdentifyRepository;
import com.Bank.BankBackend.modules.account.infrastructure.persistence.entity.TypeIdentifyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class TypeIdentifyRepositoryImpl implements TypeIdentifyRepository {

    private final TypeIdentifyMapper typeIdentifyMapper;
    private final TypeIdentifyJpaRepository typeIdentifyJpaRepository;

    @Override
    public TypeIdentify save(TypeIdentify typeIdentify) {
        TypeIdentifyEntity entity = typeIdentifyMapper.toEntity(typeIdentify);
        TypeIdentifyEntity savedEntity = typeIdentifyJpaRepository.save(entity);
        return typeIdentifyMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<TypeIdentify> findByName(String name) {
        return typeIdentifyJpaRepository
                .findByName(name)
                .map(typeIdentifyMapper::toDomain);
    }

    @Override
    public List<TypeIdentify> findAll() {
        return typeIdentifyJpaRepository
                .findAll()
                .stream()
                .map(typeIdentifyMapper::toDomain)
                .toList();
    }
}
