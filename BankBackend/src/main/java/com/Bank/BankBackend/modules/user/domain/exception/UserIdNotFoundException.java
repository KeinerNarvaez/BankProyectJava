package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class UserIdNotFoundException extends BusinessException {

    public UserIdNotFoundException(Integer userId) {
        super("USER_NOT_FOUND", "El usuario con ID " + userId + " no existe");
    }
}
