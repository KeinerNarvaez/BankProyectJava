package com.Bank.BankBackend.modules.user.application.dto.request;

import com.Bank.BankBackend.modules.user.domain.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordResetTokenRequest {
    @NotNull(message = "El ID de la sección es obligatorio")
    private Integer passwordResetTokenId;
    @NotNull(message = "El ID del usuario es obligatorio" )
    private User userId;
    @NotBlank(message = "El token es obligatorio" )
    @Size(max = 6, message = "El token de verificacion no puede superar 6 caracteres")
    private String token;
    @NotNull(message = "El campo fecha expiración obligatorio" )
    private LocalDateTime expire;
    @NotNull(message = "El campo token usado es obligatorio" )
    private boolean used;
    @NotNull(message = "La fecha de creacion es obligatorio" )
    private LocalDateTime createdAt;
}
