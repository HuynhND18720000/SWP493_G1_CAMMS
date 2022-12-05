package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.repository.IManufacturerRepository;
import com.example.swp493_g1_camms.services.interfaceService.ValidCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ValidCheckServiceImpl implements ValidCheckService {
    @Autowired
    IManufacturerRepository iManufacturerRepository;
    public void validDate(LocalDateTime dateFrom1, LocalDateTime dateTo1, String dateFrom, String dateTo ){
        if(dateFrom == null || dateFrom.equalsIgnoreCase("")){
            dateFrom1 = null;
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateFrom, formatter);
            dateFrom1 = dateTime;
        }
        if(dateTo == null || dateTo.equalsIgnoreCase("")){
            dateTo1 = null;
        }else {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime1 = LocalDateTime.parse(dateTo, formatter1);
            dateTo1 = dateTime1;
        }
    }

    public boolean isEmailManufacturerExist(String email){
        Manufacturer manufacturer = iManufacturerRepository.findManufactorByEmail(email);
        if(manufacturer == null){
            return false;
        }else{
            return true;
        }
    }
}
