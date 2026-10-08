package com.Bank.BankBackend.modules.account.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateClientRequest {
    @NotNull(message = "El número de identificación es obligatorio")
    private Integer identifyNumber;
}
