package com.Bank.BankBackend.modules.user.application.port.out;

public interface EmailNotificationPort {
    void sendActivationEmail(String toEmail, String activationCode);
    void sendPasswordRecoveryEmail(String toEmail, String recoveryCode);
}
