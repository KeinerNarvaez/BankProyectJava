package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class CodeNotValidException extends BusinessException {
    public CodeNotValidException() {
        super("CODE_NOT_VALIDE","Código expirado" );
    }
}
