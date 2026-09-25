package com.Bank.BankBackend.modules.user.application.port.in;

import com.Bank.BankBackend.modules.user.application.dto.request.ChangeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.CreatePasswordResetTokenRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.ForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.request.VerifyCodeForgotPasswordRequest;
import com.Bank.BankBackend.modules.user.application.dto.response.VerifyCodeForgotPasswordResponse;

public interface ForgotPasswordPort {
    void forgotPassword(ForgotPasswordRequest request);
    VerifyCodeForgotPasswordResponse verifyCode(VerifyCodeForgotPasswordRequest request);
    void changePassword(ChangeForgotPasswordRequest request);
}
