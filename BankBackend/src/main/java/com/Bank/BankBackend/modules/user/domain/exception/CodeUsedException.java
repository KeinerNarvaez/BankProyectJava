package com.Bank.BankBackend.modules.user.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class CodeUsedException extends BusinessException {
    public CodeUsedException() {
        super("CODE_USED","Código ya usado");
    }
}
