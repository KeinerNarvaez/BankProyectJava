package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.request.UserDeleteRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserDeleteResponse;

public interface UserDeletePort {
    UserDeleteResponse delete(UserDeleteRequest request);
}