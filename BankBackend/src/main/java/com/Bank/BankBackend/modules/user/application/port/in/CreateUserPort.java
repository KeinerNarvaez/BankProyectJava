package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.request.CreateUserRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserResponse;

public interface CreateUserPort {

    UserResponse create(CreateUserRequest request);
}
