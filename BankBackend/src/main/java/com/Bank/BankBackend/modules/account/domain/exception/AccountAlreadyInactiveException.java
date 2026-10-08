package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountAlreadyInactiveException extends BusinessException {
    public AccountAlreadyInactiveException() {
        super("ACCOUNT_ALREADY_ACTIVE", "La cuenta ya se encuentra inactivada.");
    }
}
