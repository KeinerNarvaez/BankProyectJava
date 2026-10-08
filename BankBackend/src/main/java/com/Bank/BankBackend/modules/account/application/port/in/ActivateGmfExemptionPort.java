package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateGmfExemptionRequest;

public interface ActivateGmfExemptionPort {
    void activate(ActivateGmfExemptionRequest request);
}
