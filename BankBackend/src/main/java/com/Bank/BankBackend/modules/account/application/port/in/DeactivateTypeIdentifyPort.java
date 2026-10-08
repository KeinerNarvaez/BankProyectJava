package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateTypeIdentifyRequest;

public interface DeactivateTypeIdentifyPort {
    void deactivate(DeactivateTypeIdentifyRequest request);
}
