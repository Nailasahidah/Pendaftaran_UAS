package com.nailasahidah.cashier.service.implementation;

import com.nailasahidah.cashier.enumeration.VerificationType;
import com.nailasahidah.cashier.exception.ApiException;
import com.nailasahidah.cashier.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(
            String fullName,
            String email,
            String verificationUrl,
            VerificationType verificationType) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nailasahidah017@gmail.com");
            message.setTo(email);
            message.setText(getEmailMessage(fullName, verificationUrl, verificationType));
            message.setSubject(String.format("User Management - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
            mailSender.send(message);
            log.info("Email sent to {}", fullName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private String getEmailMessage(String fullName, String verificationUrl, VerificationType verificationType) {
        switch (verificationType) {
            case PASSWORD -> {
                return "Hello " + fullName +
                        "\n\nReset password request. Please click the link below to reset your password. \n\n" + verificationUrl +
                        "\n\nThe Support Team"; }
            case ACCOUNT -> {
                return "Hello " + fullName +
                        "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + verificationUrl +
                        "\n\nThe Support Team";
            }
            default -> throw new ApiException("Unable to send email. Email type unknown");
        }
    }
}
