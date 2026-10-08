package com.Bank.BankBackend.modules.gmf.application.port.in;

import java.math.BigDecimal;

public interface RefundPendingGmfPort {
    BigDecimal refund(Integer accountId);
}
