package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.FillOTPRequest;
import com.example.swp493_g1_camms.payload.request.GetEmailRequest;
import com.example.swp493_g1_camms.payload.request.ResetPasswordRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.UserServiceImpl;
import com.example.swp493_g1_camms.services.interfaceService.IUserService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ForgotPasswordController {
    @Autowired
    IUserService userService;

    @PostMapping("/forgot_password")
    ResponseEntity<?> forgotPassword(@RequestBody GetEmailRequest email_request) {
        return userService.getEmailInForgotPassword(email_request.getEmail_request());
    }
    @PostMapping("/check_otp")
    ResponseEntity<?> checkOTP(@RequestBody FillOTPRequest checkOtp) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return userService.checkOTPFromClient(checkOtp.getOtp());
    }

    @PutMapping("/reset_password")
    ResponseEntity<?> resetYourPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return userService.resetPassword(resetPasswordRequest);
    }
}
