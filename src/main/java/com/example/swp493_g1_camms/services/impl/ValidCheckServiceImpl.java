package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.entities.Order;
import com.example.swp493_g1_camms.entities.OrderType;
import com.example.swp493_g1_camms.repository.IManufacturerRepository;
import com.example.swp493_g1_camms.repository.IOrderRepository;
import com.example.swp493_g1_camms.services.interfaceService.IValidCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ValidCheckServiceImpl implements IValidCheckService {

    @Autowired
    IManufacturerRepository manufacturerRepository;

    @Autowired
    IOrderRepository orderRepository;

    @Override
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

    @Override
    public boolean isReturnOrderCodeExist(String orderCode) {
        List<Order> order = orderRepository.getOrderReturnByOrderCode(orderCode);
        if(order == null || order.size() == 0){
            return false;
        }else{
            return true;
        }
    }
}
