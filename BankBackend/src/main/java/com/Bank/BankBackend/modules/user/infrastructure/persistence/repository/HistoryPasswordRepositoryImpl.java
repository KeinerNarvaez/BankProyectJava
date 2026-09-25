package com.Bank.BankBackend.modules.user.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.user.application.mapper.HistoryPasswordMapper;
import com.Bank.BankBackend.modules.user.domain.model.HistoryPassword;
import com.Bank.BankBackend.modules.user.domain.repository.HistoryPasswordRepository;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.HistoryPasswordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HistoryPasswordRepositoryImpl implements HistoryPasswordRepository {

    private final HistoryPasswordJpaRepository historyPasswordJpaRepository;
    private final HistoryPasswordMapper historyPasswordMapper;

    @Override
    public HistoryPassword save(HistoryPassword historyPassword) {
        HistoryPasswordEntity entity = historyPasswordMapper.toEntity(historyPassword);
        HistoryPasswordEntity savedEntity = historyPasswordJpaRepository.save(entity);
        return historyPasswordMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<HistoryPassword> findById(Integer historyPasswordId) {
        return historyPasswordJpaRepository.findById(historyPasswordId)
                .map(historyPasswordMapper::toDomain);
    }

    @Override
    public List<HistoryPassword> findByUserId(Integer userId) {
        return historyPasswordJpaRepository.findByUserId_UserId(userId)
                .stream()
                .map(historyPasswordMapper::toDomain)
                .toList();
    }
}