package com.Bank.BankBackend.modules.account.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Integer clientId;
    private TypeIdentify typeIdentify;
    private String names;
    private String lastNames;
    private Integer identifyNumber;
    private TypeStatus status;
    private String email;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer userCreation;
}
