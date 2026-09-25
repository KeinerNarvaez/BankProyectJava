package com.Bank.BankBackend.modules.user.application.dto.response;

public record UserListResponse(
        Integer userId,
        String email
) {}
