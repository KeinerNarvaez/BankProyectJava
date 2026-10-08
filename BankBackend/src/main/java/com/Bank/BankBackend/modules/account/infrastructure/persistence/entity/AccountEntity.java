package com.Bank.BankBackend.modules.account.infrastructure.persistence.entity;

import com.Bank.BankBackend.modules.account.domain.model.AccountStatus;
import com.Bank.BankBackend.modules.account.domain.model.Client;
import com.Bank.BankBackend.modules.account.domain.model.TypeAccount;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id",nullable = false)
    private Integer accountId;
    @Column(name = "account_number",nullable = false,length = 10)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_account", nullable = false,length = 20)
    private TypeAccount typeAccount;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status",nullable = false, length = 20)
    private AccountStatus accountStatus;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "available_balance", nullable = false)
    private BigDecimal availableBalance;
    @Column(name = "gmf_exempt", nullable = false)
    private Boolean gmfExempt;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",nullable = false)
    private ClientEntity clientId;
    @Column(name = "user_creation_id",nullable = false)
    private Integer userCreationId;
}
