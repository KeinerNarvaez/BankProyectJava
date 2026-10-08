package com.Bank.BankBackend.modules.account.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetListClientResponse {
    private Integer clientId;
    private String names;
    private String lastNames;
    private Integer identifyNumber;
    private String email;
}
