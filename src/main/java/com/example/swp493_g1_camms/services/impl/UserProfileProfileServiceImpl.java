package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IUserRepository;
import com.example.swp493_g1_camms.security.services.AuthenticationFacade;
import com.example.swp493_g1_camms.services.interfaceService.IUserProfileService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserProfileProfileServiceImpl implements IUserProfileService {
    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    IUserRepository IUserRepository;
    @Override
    public ResponseEntity<?> getUserProfile() {
        String usernameOfCurrentUser = authenticationFacade.currentUserNameSimple();
        System.out.println("user hien tai la "+usernameOfCurrentUser);
        Optional<User> user = IUserRepository.findByUsername(usernameOfCurrentUser);
        boolean currentUserIsActive = CurrentUserIsActive.currentUserIsActive();
        ResponseVo responseVo = new ResponseVo();
        if(!currentUserIsActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Tài khoản của bạn đã bị tạm dừng!", StatusUtils.NOT_Allow));

        }
        responseVo.setData(user);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

}