package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.TransferRequest;

public interface TransferPort {
    void transfer(Integer userId,TransferRequest request);
}
