package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.request.UserActivationTokenRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.VerifyCodeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.UserActivationResponse;
import com.Bank.BankBackend.modules.user.application.dto.response.VerifyCodeForgotPasswordResponse;

public interface UserActivationPort {
    UserActivationResponse userActivation(UserActivationTokenRequest request);
}
