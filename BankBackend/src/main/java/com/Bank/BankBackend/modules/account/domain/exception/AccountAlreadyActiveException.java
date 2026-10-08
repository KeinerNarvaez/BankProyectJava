package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountAlreadyActiveException extends BusinessException {

    public AccountAlreadyActiveException() {
        super("ACCOUNT_ALREADY_ACTIVE", "La cuenta ya se encuentra activa.");
    }
}
