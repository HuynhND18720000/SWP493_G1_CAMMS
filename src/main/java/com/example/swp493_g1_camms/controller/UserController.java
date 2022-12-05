package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.services.impl.UserProfileProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserProfileProfileServiceImpl userProfileService;

    @GetMapping("/userprofile")
    public ResponseEntity<?> getUserProfile() {
        return userProfileService.getUserProfile();
    }

    @PutMapping("/change_password")
    public ResponseEntity<?> updatePassword(){
        return null;
    }
}
