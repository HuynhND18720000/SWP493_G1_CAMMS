package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.ChangePasswordRequest;
import com.example.swp493_g1_camms.payload.request.UserProfileRequest;
import org.springframework.http.ResponseEntity;

public interface IUserProfileService {
    ResponseEntity<?> getUserProfile();

    ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest);

    ResponseEntity<?> updateProfile(UserProfileRequest userProfileRequest);
}
