package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientEmailAlreadyExistsException extends BusinessException {
    public ClientEmailAlreadyExistsException(String email) {
        super("CLIENT_ALREADY_EXISTS", "Existe un usuario con ese correo electrónico: " + email);
    }
}
