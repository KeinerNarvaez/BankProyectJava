package com.Bank.BankBackend.modules.account.application.dto.request;

import com.Bank.BankBackend.modules.account.domain.model.TypeAccount;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TypeAccount typeAccount;
    @NotNull(message = "El ID de la sección de cliente es obligatorio")
    private Integer clientId;
}
