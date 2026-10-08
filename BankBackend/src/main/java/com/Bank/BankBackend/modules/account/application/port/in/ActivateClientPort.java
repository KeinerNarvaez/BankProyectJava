package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateClientRequest;

public interface ActivateClientPort {
    void activateClient(ActivateClientRequest request);
}
