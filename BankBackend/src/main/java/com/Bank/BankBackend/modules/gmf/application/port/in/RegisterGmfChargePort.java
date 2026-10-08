package com.Bank.BankBackend.modules.gmf.application.port.in;

import java.math.BigDecimal;

public interface RegisterGmfChargePort {
    void register(Integer transactionId, Integer accountId, BigDecimal amount);
}
