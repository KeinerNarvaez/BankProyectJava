package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class UserEmailAlreadyExistsException extends BusinessException {
    public UserEmailAlreadyExistsException(String email) {
        super("PROFILE_ALREADY_EXISTS", "Existe un usuario con ese correo electrónico: " + email);
    }
}
