package com.Bank.BankBackend.modules.user.application.dto.request;

import com.Bank.BankBackend.modules.user.domain.model.RolType;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull(message = "El ID de la sección es obligatorio")
    private Integer userId;
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 100, message = "El correo electrónico no puede superar 100 caracteres")
    @Email
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 100 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String password;
    @NotNull(message = "El campo estado es obligatorio" )
    private UserStatus userStatus;
    @NotNull(message = "El campo de rol es obligatorio")
    private RolType rolType;
    @NotNull(message = "El campo fecha de creacion es obligatorio")
    private LocalDateTime createdAt;
    @NotNull(message = "La campo fecha de actualización es obligatorio")
    private LocalDateTime updatedAt;
}
