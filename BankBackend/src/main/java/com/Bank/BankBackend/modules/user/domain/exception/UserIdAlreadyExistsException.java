package com.Bank.BankBackend.modules.user.domain.exception;


import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class UserIdAlreadyExistsException extends BusinessException {
    public UserIdAlreadyExistsException(Integer userId) {
        super("PROFILE_ALREADY_EXISTS", "Existe un usuario con ese ID: " + userId);
    }

}
