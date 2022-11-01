package com.example.swp493_g1_camms.utils;

import com.example.swp493_g1_camms.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserIsActive {
    public static boolean currentUserIsActive(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean currentUserActive = true;
        if(principal instanceof UserDetailsImpl){
            currentUserActive = ((UserDetailsImpl) principal).isActive();
        }
        if(!currentUserActive){
            currentUserActive= false;
        }
        return  currentUserActive;
    }
}
