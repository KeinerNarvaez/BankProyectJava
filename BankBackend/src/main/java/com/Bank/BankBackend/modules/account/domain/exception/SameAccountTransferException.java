package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class SameAccountTransferException extends BusinessException {
    public SameAccountTransferException() {
        super("SAME_ACCOUNT_TRANSFER","No se pueden hacer transferencia entre cuentas iguales.");
    }
}
