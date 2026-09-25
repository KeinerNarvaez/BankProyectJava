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
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_reset_token_id", nullable = false)
    private Integer passwordResetTokenId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userId;
    @Column(name = "token",nullable = false, length = 6)
    private String token;
    @Column(name = "expire",nullable = false)
    private LocalDateTime expire;
    @Column(name = "used",nullable = false)
    private boolean used;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

}
