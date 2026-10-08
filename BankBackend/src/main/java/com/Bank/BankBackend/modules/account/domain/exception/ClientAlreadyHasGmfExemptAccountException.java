package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientAlreadyHasGmfExemptAccountException extends BusinessException {
    public ClientAlreadyHasGmfExemptAccountException() {
        super("GMF_ALREADY_ACTIVE","Alguna cuenta ya se encuentra exenta al GMF.");
    }
}
