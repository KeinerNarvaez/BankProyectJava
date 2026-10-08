package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountCanceledException extends BusinessException {
    public AccountCanceledException() {
        super("ACCOUNT_CANCELLED","La cuenta se encuentra desactivada.");
    }
}
