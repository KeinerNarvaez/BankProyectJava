package com.Bank.BankBackend.modules.account.infrastructure.persistence.entity;

import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Integer clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_identify",nullable = false)
    private TypeIdentifyEntity typeIdentify;
    @Column(name = "name", nullable = false, length = 100)
    private String names;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastNames;
    @Column(name = "identify_number", nullable = false, length = 10,unique = true)
    private Integer identifyNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false,length = 20)
    private TypeStatus status;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "user_creation")
    private Integer userCreation;
}
