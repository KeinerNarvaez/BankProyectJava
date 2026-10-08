package com.Bank.BankBackend.modules.account.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class WithdrawAllResponse {
    private BigDecimal withdraw;
}
