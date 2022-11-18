package com.example.swp493_g1_camms.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ConvertDateUtils {
    public LocalDateTime convertDateFormat(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = new Date();
        String dateFormat = formatter.format(today);
        LocalDateTime ldt = LocalDateTime.ofInstant(today.toInstant(),
                ZoneId.systemDefault());
        return ldt;
    }
}
