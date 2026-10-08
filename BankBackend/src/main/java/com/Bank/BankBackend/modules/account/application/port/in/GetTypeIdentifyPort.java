package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.domain.model.TypeIdentify;

import java.util.List;

public interface GetTypeIdentifyPort {
    List<TypeIdentify> getAllIdentify();
}
