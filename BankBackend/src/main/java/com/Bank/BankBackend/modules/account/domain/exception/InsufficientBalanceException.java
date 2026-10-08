package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class InsufficientBalanceException extends BusinessException {

    public InsufficientBalanceException() {
        super("INSUFFICIENT_BALANCE", "La cuenta no tiene saldo suficiente para realizar el retiro.");
    }
}
