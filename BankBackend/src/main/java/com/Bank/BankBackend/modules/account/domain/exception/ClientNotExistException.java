package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientNotExistException extends BusinessException {
    public ClientNotExistException() {
        super("CLIENTS_NOT_EXIST","No existe cliente.");
    }
}
