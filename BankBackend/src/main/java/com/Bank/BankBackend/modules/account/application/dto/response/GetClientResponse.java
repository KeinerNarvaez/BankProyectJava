package com.Bank.BankBackend.modules.account.application.dto.response;

import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;
import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Builder
public class GetClientResponse {
    private Integer clientId;
    private TypeIdentify typeIdentify;
    private String names;
    private String lastNames;
    private Integer identifyNumber;
    private String email;
    private TypeStatus status;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer userCreation;
}
