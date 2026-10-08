package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.CancelAccountRequest;

public interface CanceledAccountPort {
    void canceledAccount(CancelAccountRequest request);
}
