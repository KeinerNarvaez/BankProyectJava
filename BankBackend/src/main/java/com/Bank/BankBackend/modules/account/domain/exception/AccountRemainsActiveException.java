package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountRemainsActiveException extends BusinessException {
    public AccountRemainsActiveException() {
        super("ACCOUNT_REMAINS_ACTIVE", "Aun permanece cuenta o cuentas activa.");
    }
}
