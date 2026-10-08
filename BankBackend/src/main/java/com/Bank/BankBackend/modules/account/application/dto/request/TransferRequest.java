package com.Bank.BankBackend.modules.account.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "El número de identificación es obligatorio")
    private Integer identifyNumber;

    @NotBlank(message = "La cuenta de origen es obligatoria")
    @Pattern(regexp = "\\d{10}", message = "La cuenta de origen debe contener exactamente 10 dígitos")
    private String originAccountNumber;

    @NotBlank(message = "La cuenta de destino es obligatoria")
    @Pattern(regexp = "\\d{10}", message = "La cuenta de destino debe contener exactamente 10 dígitos")
    private String destinationAccountNumber;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor de la transferencia debe ser mayor que cero")
    private BigDecimal amount;

    private String description;
}