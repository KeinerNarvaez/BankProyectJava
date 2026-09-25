package com.Bank.BankBackend.modules.user.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String email;
    private String password;
    private UserStatus userStatus;
    private RolType rolType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
