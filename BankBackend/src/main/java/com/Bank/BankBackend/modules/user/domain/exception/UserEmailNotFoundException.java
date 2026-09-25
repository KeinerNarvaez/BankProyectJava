package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class UserEmailNotFoundException extends BusinessException {
    public UserEmailNotFoundException(String email) {
        super("USER_NOT_FOUND", "El usuario con correo electrónico" + email + " no existe");
    }
}
