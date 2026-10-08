package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class TypeIdentifyNameAlreadyExistsException extends BusinessException {
    public TypeIdentifyNameAlreadyExistsException() {
        super("TYPEIDENTIFY_ALREADY_EXISTS","Ya existe el tipo de identificación.");
    }
}
