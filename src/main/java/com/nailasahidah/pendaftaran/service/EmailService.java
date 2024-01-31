package com.nailasahidah.pendaftaran.service;

import com.nailasahidah.pendaftaran.enumeration.VerificationType;

public interface EmailService {
    void sendVerificationEmail(String fullName,
                               String email,
                               String verificationUrl,
                               VerificationType verificationType);
}
