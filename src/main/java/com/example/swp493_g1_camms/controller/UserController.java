package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ChangePasswordRequest;
import com.example.swp493_g1_camms.payload.request.UserProfileRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.UserProfileServiceImpl;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserProfileServiceImpl userProfileService;

    @GetMapping("/userprofile")
    public ResponseEntity<?> getUserProfile() {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return userProfileService.getUserProfile();
    }

    @PutMapping("/userprofile/change_password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return userProfileService.changePassword(changePasswordRequest);
    }

    @PutMapping("/userprofile/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileRequest userProfileRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return userProfileService.updateProfile(userProfileRequest);
    }
}
