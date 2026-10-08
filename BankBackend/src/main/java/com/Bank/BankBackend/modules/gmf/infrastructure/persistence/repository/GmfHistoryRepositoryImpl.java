package com.Bank.BankBackend.modules.gmf.infrastructure.persistence.repository;

import com.Bank.BankBackend.modules.gmf.application.mapper.GmfHistoryMapper;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfHistory;
import com.Bank.BankBackend.modules.gmf.domain.model.GmfOperationType;
import com.Bank.BankBackend.modules.gmf.domain.repository.GmfHistoryRepository;
import com.Bank.BankBackend.modules.gmf.infrastructure.persistence.entity.GmfHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GmfHistoryRepositoryImpl implements GmfHistoryRepository {

    private final GmfHistoryJpaRepository gmfHistoryJpaRepository;
    private final GmfHistoryMapper gmfHistoryMapper;

    @Override
    public GmfHistory save(GmfHistory gmfHistory) {

        GmfHistoryEntity entity =
                gmfHistoryMapper.toEntity(gmfHistory);

        GmfHistoryEntity savedEntity =
                gmfHistoryJpaRepository.save(entity);

        return gmfHistoryMapper.toDomain(savedEntity);
    }

    @Override
    public List<GmfHistory> findPendingChargesByAccountId(
            Integer accountId
    ) {

        return gmfHistoryJpaRepository
                .findAllByAccountIdAndOperationType(
                        accountId,
                        GmfOperationType.CHARGE
                )
                .stream()
                .map(gmfHistoryMapper::toDomain)
                .toList();
    }
}
