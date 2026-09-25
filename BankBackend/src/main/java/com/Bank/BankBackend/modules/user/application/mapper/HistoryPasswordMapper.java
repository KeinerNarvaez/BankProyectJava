package com.Bank.BankBackend.modules.user.application.mapper;

import com.Bank.BankBackend.modules.user.domain.model.HistoryPassword;
import com.Bank.BankBackend.modules.user.infrastructure.persistence.entity.HistoryPasswordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface HistoryPasswordMapper {
    HistoryPassword toDomain(HistoryPasswordEntity entity);
    HistoryPasswordEntity toEntity(HistoryPassword domain);
}
