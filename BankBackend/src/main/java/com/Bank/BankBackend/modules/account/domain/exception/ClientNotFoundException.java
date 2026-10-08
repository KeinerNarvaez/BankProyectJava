package com.Bank.BankBackend.modules.account.domain.exception;

import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientNotFoundException extends BusinessException {
    public ClientNotFoundException(Integer identifyNumber) {
        super("CLIENT_NOT_FOUND","Cliente con número de identificación "+ identifyNumber + " no encontrado.");
    }
}
