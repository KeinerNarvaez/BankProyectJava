package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class CodeOrUserNotValidException extends BusinessException {
    public CodeOrUserNotValidException() {
        super("CODE_OR_USER_NOT_VALIDE","Código o usuario no valido");
    }
}
