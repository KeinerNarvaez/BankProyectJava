package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateClientRequest;

public interface CreateClientPort {
    void create(Integer userId,CreateClientRequest request);
}
