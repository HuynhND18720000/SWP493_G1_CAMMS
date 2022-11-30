package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.services.impl.UserProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserProfileServiceImpl userProfileService;

    @GetMapping("/userprofile")
    public ResponseEntity<?> getUserProfile() {
        return userProfileService.getUserProfile();
    }
}
