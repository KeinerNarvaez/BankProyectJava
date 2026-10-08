package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountInactiveException extends BusinessException {
    public AccountInactiveException() {
        super("ACCOUNT_INACTIVE","La cuenta se encuentra inactiva.");
    }
}
