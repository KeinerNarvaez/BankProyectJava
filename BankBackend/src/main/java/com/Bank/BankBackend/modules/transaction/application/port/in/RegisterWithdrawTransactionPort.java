package com.Bank.BankBackend.modules.transaction.application.port.in;

import java.math.BigDecimal;

public interface RegisterWithdrawTransactionPort {
    Integer register(Integer accountId, BigDecimal amount, Integer userId);
}
