package com.Bank.BankBackend.modules.transaction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    private Integer movementId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String description;
    private MovementType movementType;
    private Transaction transactionId;
    private Integer accountId;
}
