package com.Bank.BankBackend.modules.transaction.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String description;
    private Integer originAccountId;
    private Integer destinationAccountId;
    private Integer userId;
}
