package com.Bank.BankBackend.modules.account.infrastructure.persistence.entity;

import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "type_identify")
public class TypeIdentifyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_identify_id",nullable = false)
    private Integer typeIdentifyId;
    @Column(name = "name",nullable = false, unique = true, length = 20)
    private String name;
    @Column(name = "description",nullable = false, length = 100)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TypeStatus status;
}
