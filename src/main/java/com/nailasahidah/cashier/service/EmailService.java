package com.nailasahidah.cashier.service;

import com.nailasahidah.cashier.enumeration.VerificationType;

public interface EmailService {
    void sendVerificationEmail(String fullName,
                               String email,
                               String verificationUrl,
                               VerificationType verificationType);
}
