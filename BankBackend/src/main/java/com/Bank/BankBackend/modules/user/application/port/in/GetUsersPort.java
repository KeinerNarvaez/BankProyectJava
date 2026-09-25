package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.response.UserListResponse;

import java.util.List;


public interface GetUsersPort {
    List<UserListResponse> listUsers();
}
