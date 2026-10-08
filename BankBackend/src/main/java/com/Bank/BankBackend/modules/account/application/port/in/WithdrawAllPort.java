package com.Bank.BankBackend.modules.account.application.port.in;

import com.Bank.BankBackend.modules.account.application.dto.request.WithdrawAllRequest;
import com.Bank.BankBackend.modules.account.application.dto.response.WithdrawAllResponse;

public interface WithdrawAllPort {
    WithdrawAllResponse withdraw(Integer userid, WithdrawAllRequest request);
}
