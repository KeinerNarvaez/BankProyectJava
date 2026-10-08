package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException(String accountNumber) {
        super("ACCOUNT_NOT_FOUND", "No se encontró la cuenta con número: " + accountNumber);
    }
}
