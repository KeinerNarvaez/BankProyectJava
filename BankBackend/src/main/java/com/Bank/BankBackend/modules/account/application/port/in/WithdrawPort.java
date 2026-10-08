package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.WithdrawRequest;

public interface WithdrawPort {
    void withdraw(Integer userid, WithdrawRequest request);
}
