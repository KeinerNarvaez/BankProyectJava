package com.Bank.BankBackend.modules.user.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPassword {
    private Integer historyPasswordId;
    private User userId;
    private String password;
    private LocalDateTime createdAt;
}
