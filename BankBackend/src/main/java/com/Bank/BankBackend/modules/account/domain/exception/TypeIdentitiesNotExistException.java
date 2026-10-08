package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class TypeIdentitiesNotExistException extends BusinessException {
    public TypeIdentitiesNotExistException() {
        super("TYPE_IDENTITIES_NOT_EXIST","No existen tipos de identificación.");
    }
}
