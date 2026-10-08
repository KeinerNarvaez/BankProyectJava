package com.Bank.BankBackend.modules.account.application.dto.request;

import com.Bank.BankBackend.modules.account.domain.model.TypeStatus;
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
public class CreateTypeIdentifyRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 20,message = "El nombre del tipo de cuenta no debe superar los 20 caracteres")
    private String name;
    @NotBlank(message = "La descripción del tipo de cuenta es obligatorio")
    @Size(max = 100, message = "La descripción de el tipo de cuenta no puede")
    private String description;
}
