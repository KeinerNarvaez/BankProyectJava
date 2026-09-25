package com.Bank.BankBackend.modules.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeForgotPasswordRequest {
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 100, message = "El correo electrónico no puede superar 100 caracteres")
    @Email
    private String email;
    @NotBlank(message = "El token es obligatorio" )
    @Size(max = 6, message = "El token de verificacion no puede superar 6 caracteres")
    private String verificationCode;
}
