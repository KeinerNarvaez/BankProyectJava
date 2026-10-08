package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class AccountDoesNotBelongToClientException extends BusinessException {

    public AccountDoesNotBelongToClientException() {
        super("ACCOUNT_DOES_NOT_BELONG_TO_CLIENT", "La cuenta no pertenece al cliente indicado.");
    }
}
