package com.Bank.BankBackend.modules.user.domain.model;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    private Integer passwordResetTokenId;
    private User userId;
    private String token;
    private LocalDateTime expire;
    private boolean used;
    private LocalDateTime createdAt;

}
