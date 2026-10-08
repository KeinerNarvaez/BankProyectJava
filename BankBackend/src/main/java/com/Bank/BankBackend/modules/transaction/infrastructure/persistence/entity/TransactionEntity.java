package com.Bank.BankBackend.modules.transaction.infrastructure.persistence.entity;

import com.Bank.BankBackend.modules.transaction.domain.model.TransactionType;
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
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id",nullable = false)
    private Integer transactionId;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type",nullable = false,length = 20)
    private TransactionType transactionType;
    @Column(name = "amount",nullable = false)
    private BigDecimal amount;
    @Column(name = "transaction_date",nullable = false)
    private LocalDateTime transactionDate;
    @Column(name = "description",nullable = false, length = 250)
    private String description;
    @Column(name = "origin_account_id",nullable = false)
    private Integer originAccountId;
    @Column(name = "destination_account_id",nullable = false)
    private Integer destinationAccountId;
    @Column(name = "user_id",nullable = false)
    private Integer userId;
}
