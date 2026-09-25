package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.request.ChangePasswordRequest;


public interface ChangePasswordPort {
    void changePassword(Integer userId,ChangePasswordRequest request);
}
