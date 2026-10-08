package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.DepositRequest;

public interface DepositPort {
    void deposit(Integer userId,DepositRequest request);
}
