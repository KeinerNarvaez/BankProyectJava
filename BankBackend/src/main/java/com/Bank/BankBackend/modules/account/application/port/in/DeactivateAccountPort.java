package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.DeactivateAccountRequest;

public interface DeactivateAccountPort {
    void deactivateAccount(DeactivateAccountRequest request);
}
