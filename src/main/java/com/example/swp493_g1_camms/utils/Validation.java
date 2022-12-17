package com.example.swp493_g1_camms.utils;

import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.entities.Warehouse;
import com.example.swp493_g1_camms.repository.IManufacturerRepository;
import com.example.swp493_g1_camms.repository.IWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Validation {
    @Autowired
    IManufacturerRepository manufacturerRepository;
    @Autowired
    IWarehouseRepository warehouseRepository;

    public LocalDateTime validDate(LocalDateTime date1, String date) {
        if(date == null || date.equalsIgnoreCase("")){
            date1 = null;
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            date1 = dateTime;
        }
        return date1;
    }

    public boolean isEmailManufacturerExist(String email){
        Manufacturer manufacturer = manufacturerRepository.findManufactorByEmail(email);
        if(manufacturer == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean isWarehouseNameExist(String warehouseName){
        Warehouse warehouse = warehouseRepository.findWarehouseByName(warehouseName);
        if(warehouse == null){
            return false;
        }else{
            return true;
        }
    }
}
