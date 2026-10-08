package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class TypeIdentifyInactiveException extends BusinessException {
    public TypeIdentifyInactiveException() {
        super("TYPE_IDENTIFY_INACTIVE","El tipo de identificación se encuentra inactiva.");;
    }
}
