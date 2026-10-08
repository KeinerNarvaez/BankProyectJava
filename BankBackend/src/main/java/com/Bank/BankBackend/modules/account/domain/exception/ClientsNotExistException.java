package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientsNotExistException extends BusinessException {
    public ClientsNotExistException() {
        super("CLIENTS_NOT_EXIST","No existen clientes.");
    }
}
