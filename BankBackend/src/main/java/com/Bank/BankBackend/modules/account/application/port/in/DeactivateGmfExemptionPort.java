package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateGmfExemptionRequest;

public interface DeactivateGmfExemptionPort {
    void deactivate(DeactivateGmfExemptionRequest request);
}
