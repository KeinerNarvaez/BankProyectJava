package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientAlreadyActiveException extends BusinessException {
    public ClientAlreadyActiveException() {
        super("CLIENT_ALREADY_ACTIVE","El cliente ya se encuentra activo.");
    }
}