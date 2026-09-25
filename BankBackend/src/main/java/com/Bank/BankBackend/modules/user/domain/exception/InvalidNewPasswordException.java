package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class InvalidNewPasswordException extends BusinessException {
    public InvalidNewPasswordException(){
        super("IS_NEW_PASSWORD_SAME_AS_OLD","La contraseña nueva no puede ser como las anteriores");
    }

}
