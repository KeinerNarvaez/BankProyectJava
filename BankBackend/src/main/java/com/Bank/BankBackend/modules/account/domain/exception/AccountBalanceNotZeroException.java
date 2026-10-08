package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountBalanceNotZeroException extends BusinessException {
    public AccountBalanceNotZeroException() {
        super("ACCOUNT_BALANCE_NOT_ZERO","Saldo de cuenta distinto de cero");
    }
}
