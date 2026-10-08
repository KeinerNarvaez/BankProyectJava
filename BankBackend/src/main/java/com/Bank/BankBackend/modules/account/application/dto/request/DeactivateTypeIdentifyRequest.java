package com.Bank.BankBackend.modules.account.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateTypeIdentifyRequest {
    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(max = 20, message = "El nombre no puede superar los 20 caracteres")
    private String name;
}
