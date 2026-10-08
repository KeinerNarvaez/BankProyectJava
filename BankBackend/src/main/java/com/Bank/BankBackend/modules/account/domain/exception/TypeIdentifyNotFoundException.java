package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class TypeIdentifyNotFoundException extends BusinessException {
    public TypeIdentifyNotFoundException() {
        super("TYPE_IDENTIFY_NOT_FOUND","No se encontro el tipo de identificación.");
    }
}
