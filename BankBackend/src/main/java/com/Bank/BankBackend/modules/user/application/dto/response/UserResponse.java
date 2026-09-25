package com.Bank.BankBackend.modules.user.application.dto.response;

import com.Bank.BankBackend.modules.user.domain.model.RolType;
import com.Bank.BankBackend.modules.user.domain.model.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private Integer userId;
    private String email;
    private UserStatus UserStatus;
    private RolType RolType;
}
