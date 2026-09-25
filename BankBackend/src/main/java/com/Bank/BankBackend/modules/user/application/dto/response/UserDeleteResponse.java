package com.Bank.BankBackend.modules.user.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDeleteResponse {
    private boolean valid;
    private String message;
}
