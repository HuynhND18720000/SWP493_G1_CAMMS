package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<?> getEmailInForgotPassword(String email);

    ResponseEntity<?> checkOTPFromClient(String otp);

    ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);

}
