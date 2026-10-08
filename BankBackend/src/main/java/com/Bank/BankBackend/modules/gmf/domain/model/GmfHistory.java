package com.Bank.BankBackend.modules.gmf.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Builder(toBuilder = true)
public class GmfHistory {
    private Integer gmfHistoryId;
    private Integer accountId;
    private Integer transactionId;
    private BigDecimal amount;
    private GmfOperationType operationType;
    private LocalDateTime createdAt;
    private LocalDateTime refundedAt;
}
