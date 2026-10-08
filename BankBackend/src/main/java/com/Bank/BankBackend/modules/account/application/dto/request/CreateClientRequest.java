package com.Bank.BankBackend.modules.account.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {
    @NotNull(message = "El tipo de identificador es obligatorio")
    private String typeIdentifyName;
    @NotBlank(message = "El o los nombres son obligatorio")
    @Size(max = 100, message = "El o los nombres no puede ser menor de 2 caracteres o mayor a 100")
    private String names;
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 3,max = 100, message = "Los apellidos no puede ser menor de 2 caracteres o mayor a 100")
    private String lastNames;
    @NotNull(message = "El número de identificación es obligatorio")
    private Integer identifyNumber;
    @Email
    @NotBlank(message = "El campo de correo electrónico es obligatorio")
    @Size(max = 100, message = "El correo electrónico no puede superar 100 caracteres")
    private String email;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate birthday;
}
