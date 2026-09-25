package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException() {
        super("INCORRECT_CREDENTIALS","Credenciales incorractas, correo o contraseña no valida");
    }
}