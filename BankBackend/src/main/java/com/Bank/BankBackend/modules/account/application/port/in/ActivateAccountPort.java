package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.ActivateAccountRequest;

public interface ActivateAccountPort {
    void activateAccount(ActivateAccountRequest request);
}
