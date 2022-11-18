package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.ImportOrderImpl;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ImportOrderController {
    @Autowired
    ImportOrderImpl importOrder;

    @PostMapping(path = "/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody ImportOrderRequest importOrderRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return importOrder.createOrder(importOrderRequest);
    }
    @GetMapping(path = "/import/getAllProductByManufacturer/{id}")
    public ResponseEntity<?> getAllProductByManufacturerId(@PathVariable Long id){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return importOrder.getProductByManufacturer(id);
    }
}
