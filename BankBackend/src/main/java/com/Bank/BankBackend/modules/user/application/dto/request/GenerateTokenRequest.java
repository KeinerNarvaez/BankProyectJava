package com.Bank.BankBackend.modules.user.application.dto.request;

import com.Bank.BankBackend.modules.user.domain.model.RolType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenerateTokenRequest {
    @NotNull(message = "El ID de la sección es obligatorio")
    private Integer userId;
    @NotNull(message = "El campo de rol es obligatorio")
    private RolType rolType;
}
