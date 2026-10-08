package com.Bank.BankBackend.modules.account.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateGmfExemptionRequest {
    @NotNull(message = "El número de identificación es obligatorio")
    private Integer identifyNumber;
    @NotNull(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El número de cuenta debe contener exactamente 10 dígitos")
    private String accountNumber;
}
