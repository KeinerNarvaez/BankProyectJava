package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientUnderageException extends BusinessException {
    public ClientUnderageException() {
        super("CLIENT_UNDERAGE","Cliente menor de edad");
    }
}
