package com.Bank.BankBackend.modules.user.application.dto.response;

import com.Bank.BankBackend.modules.user.domain.model.RolType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
}
