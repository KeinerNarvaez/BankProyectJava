package com.Bank.BankBackend.modules.gmf.application.mapper;

import com.Bank.BankBackend.modules.gmf.domain.model.GmfHistory;
import com.Bank.BankBackend.modules.gmf.infrastructure.persistence.entity.GmfHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GmfHistoryMapper {
    GmfHistory toDomain(GmfHistoryEntity entity);
    GmfHistoryEntity toEntity(GmfHistory domain);
}
