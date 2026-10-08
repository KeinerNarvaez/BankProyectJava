package com.Bank.BankBackend.modules.gmf.application.useCase;

import com.Bank.BankBackend.modules.gmf.application.port.in.CalculateGmfPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculateGmfUseCase implements CalculateGmfPort {

    private static final BigDecimal GMF_RATE = new BigDecimal("0.004");

    @Override
    public BigDecimal calculate(Boolean gmfExempt, BigDecimal amount) {

        if (Boolean.TRUE.equals(gmfExempt)) {
            return BigDecimal.ZERO;
        }

        return amount.multiply(GMF_RATE);
    }

    @Override
    public BigDecimal calculateMaxWithdrawAmount(Boolean gmfExempt, BigDecimal availableBalance) {

        if (Boolean.TRUE.equals(gmfExempt)) {
            return availableBalance;
        }

        BigDecimal divisor = BigDecimal.ONE.add(GMF_RATE);

        return availableBalance.divide(divisor,2, RoundingMode.DOWN);
    }
}
