package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.CreateTypeIdentifyRequest;

public interface CreateTypeIdentifyPort {
    void create(CreateTypeIdentifyRequest request);
}
