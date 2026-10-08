package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountGmfAlreadyInactiveException extends BusinessException {
    public AccountGmfAlreadyInactiveException() {
        super("GMF_EXEMPTION_ALREADY_INACTIVE", "la cuenta ya se encuentra con GMF.");;
    }
}
