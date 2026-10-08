package com.Bank.BankBackend.modules.transaction.application.port.in;

import java.math.BigDecimal;

public interface RegisterTransferPort {
    Integer registerTransfer(Integer originAccountId, Integer destinationAccountId, BigDecimal amount, Integer userId,String description);
}
