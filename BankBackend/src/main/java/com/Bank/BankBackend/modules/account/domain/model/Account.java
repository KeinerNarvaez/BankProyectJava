package com.Bank.BankBackend.modules.account.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer accountId;
    private String accountNumber;
    private TypeAccount typeAccount;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private Boolean gmfExempt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Client clientId;
    private Integer userCreationId;
}
