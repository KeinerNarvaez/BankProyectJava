package com.Bank.BankBackend.modules.user.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeleteRequest {
    @NotNull(message = "El ID de la sección del usuario es obligatorio")
    private Integer userId;
}
