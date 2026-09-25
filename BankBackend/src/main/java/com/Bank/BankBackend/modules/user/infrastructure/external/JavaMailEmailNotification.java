package com.Bank.BankBackend.modules.user.infrastructure.external;

import com.Bank.BankBackend.modules.user.application.port.out.EmailNotificationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class JavaMailEmailNotification implements EmailNotificationPort {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Override
    public void sendActivationEmail(String toEmail, String activationCode) {
        try {
            String htmlTemplate = StreamUtils.copyToString(
                    new ClassPathResource("mail/account-activation.html").getInputStream(),
                    StandardCharsets.UTF_8);

            String htmlContent = htmlTemplate
                    .replace("{{activationCode}}", activationCode)
                    .replace("{{expirationHours}}", "15");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Tu banco \u2014 Activa tu cuenta");
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("=== ACTIVATION EMAIL SENT SUCCESSFULLY ===");
            System.out.println("To: " + toEmail);
            System.out.println("Activation Code: " + activationCode);
        } catch (Exception e) {
            System.err.println("=== FAILED TO SEND ACTIVATION EMAIL ===");
            System.err.println("To: " + toEmail);
            System.err.println("Error: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo de envio de correo de activación", e);
        }
    }

    @Override
    public void sendPasswordRecoveryEmail(String toEmail, String recoveryCode) {
        try {
            String templatePath = "mail/password-reset.html";
            String htmlTemplate = StreamUtils.copyToString(
                    new ClassPathResource(templatePath).getInputStream(),
                    StandardCharsets.UTF_8);

            String htmlContent = htmlTemplate
                    .replace("{{verificationCode}}", recoveryCode)
                    .replace("{{expirationMinutes}}", "30");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Tu banco \u2014 Recuperaci\u00f3n de contrase\u00f1a");
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("=== PASSWORD RECOVERY EMAIL SENT SUCCESSFULLY ===");
            System.out.println("To: " + toEmail);
            System.out.println("Recovery Code: " + recoveryCode);
        } catch (Exception e) {
            System.err.println("=== FAILED TO SEND PASSWORD RECOVERY EMAIL ===");
            System.err.println("To: " + toEmail);
            System.err.println("Error: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo de envio de código de recuperación", e);
        }
    }
}
