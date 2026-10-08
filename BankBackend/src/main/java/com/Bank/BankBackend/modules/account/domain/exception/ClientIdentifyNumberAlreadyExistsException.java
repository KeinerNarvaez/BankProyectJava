package com.Bank.BankBackend.modules.account.domain.exception;


import com.Bank.BankBackend.shared.domain.exception.BusinessException;

public class ClientIdentifyNumberAlreadyExistsException extends BusinessException {
    public ClientIdentifyNumberAlreadyExistsException(Integer identifyNumber) {
        super("CLIENT_ALREADY_EXISTS", "Ya existe un cliente con el numero de identificación: " + identifyNumber);
    }

}
