package com.Bank.BankBackend.modules.gmf.infrastructure.persistence.entity;

import com.Bank.BankBackend.modules.gmf.domain.model.GmfOperationType;
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
@Table(name = "Gmf_History")
public class GmfHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gmf_history_id",nullable = false)
    private Integer gmfHistoryId;
    @Column(name = "account_id",nullable = false)
    private Integer accountId;
    @Column(name = "transaction_id",nullable = false)
    private Integer transactionId;
    @Column(name = "amount",nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type",nullable = false, length = 20)
    private GmfOperationType operationType;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "refunded_at",nullable = false)
    private LocalDateTime refundedAt;
}
