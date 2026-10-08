package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountAlreadyCancelledException extends BusinessException {
    public AccountAlreadyCancelledException() {
        super("ACCOUNT_ALREADY_CANCELLED", "La cuenta ya se encuentra cancelada.");
    }
}
