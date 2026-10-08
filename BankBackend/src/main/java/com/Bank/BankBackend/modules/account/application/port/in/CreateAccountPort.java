package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateAccountRequest;

public interface CreateAccountPort {
    void create(Integer userCreationId,CreateAccountRequest request);
}
