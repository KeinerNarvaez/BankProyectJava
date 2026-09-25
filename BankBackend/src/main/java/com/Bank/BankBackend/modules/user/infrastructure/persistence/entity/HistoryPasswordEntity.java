package com.Bank.BankBackend.modules.user.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "historyPassword")
public class HistoryPasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_password_id", nullable = false)
    private Integer historyPasswordId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userId;
    @Column(name = "password",nullable = false, length = 255)
    private String password;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
