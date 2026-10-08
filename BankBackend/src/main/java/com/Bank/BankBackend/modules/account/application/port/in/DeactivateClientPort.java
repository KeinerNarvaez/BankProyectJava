package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateClientRequest;

public interface DeactivateClientPort {
    void deactivateClient(DeactivateClientRequest request);
}
