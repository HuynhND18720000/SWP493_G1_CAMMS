package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.UserRepository;
import com.example.swp493_g1_camms.security.services.AuthenticationFacade;
import com.example.swp493_g1_camms.services.interfaceService.IUserService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class UserProfileImpl implements IUserService {
    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<?> getUserProfile() {
//        Optional<User> user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
//        boolean currentUserIsActive = CurrentUserIsActive.currentUserIsActive();
//        ResponseVo responseVo = new ResponseVo();
//        if(!currentUserIsActive){
//            return new ResponseEntity<>("dang nhap da het han",HttpStatus.BAD_REQUEST);
//        }
//        if(user.isPresent()){
//
//            return new ResponseEntity<>("Lay thong tin nguoi dung thanh cong", HttpStatus.OK);
//        }
    return null;
    }
}
