package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.request.LoginRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.LoginResponse;

public interface LoginPort {
    LoginResponse login(LoginRequest request);
}
