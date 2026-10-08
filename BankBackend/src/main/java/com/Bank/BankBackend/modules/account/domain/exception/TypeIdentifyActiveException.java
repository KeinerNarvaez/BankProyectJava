package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class TypeIdentifyActiveException extends BusinessException {
    public TypeIdentifyActiveException() {
        super("TYPE_IDENTIFY_ACTIVE","El tipo de identificación se encuentra activa.");
    }
}
