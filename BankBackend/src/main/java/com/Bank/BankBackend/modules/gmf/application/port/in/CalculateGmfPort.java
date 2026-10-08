package com.Bank.BankBackend.modules.gmf.application.port.in;

import java.math.BigDecimal;

public interface CalculateGmfPort {
    BigDecimal calculate(Boolean gmfExempt, BigDecimal amount);
    BigDecimal calculateMaxWithdrawAmount(Boolean gmfExempt, BigDecimal availableBalance);
}
