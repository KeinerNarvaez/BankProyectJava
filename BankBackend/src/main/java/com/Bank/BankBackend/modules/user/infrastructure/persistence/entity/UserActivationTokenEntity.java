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
@Table(name = "user_activation_token")
public class UserActivationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_activation_token_id", nullable = false)
    private Integer userActivationTokenId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false,unique = true)
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
