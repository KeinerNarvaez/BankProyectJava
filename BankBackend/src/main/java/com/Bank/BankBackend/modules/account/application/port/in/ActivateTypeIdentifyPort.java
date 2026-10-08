package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateTypeIdentifyRequest;

public interface ActivateTypeIdentifyPort {
    void activate(ActivateTypeIdentifyRequest request);
}
