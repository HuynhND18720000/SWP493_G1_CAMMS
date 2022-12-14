package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.interfaceService.IDashboardService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    IDashboardService dashboardService;
    @GetMapping("/totalProductInWarehouse")
    public ResponseEntity<?> getTotalProductInWarehouse(){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return dashboardService.getTotalProductInWarehouse();
    }
    @GetMapping("/totalImportOrders")
    public ResponseEntity<?> getTotalImportOrderInWarehouse(){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return dashboardService.getTotalImportOrder();
    }

    @GetMapping("/totalExportOrders")
    public ResponseEntity<?> getTotalExportOrderInWarehouse(){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return dashboardService.getTotalExportOrder();
    }
    @GetMapping("/top5ProductSales")
    public ResponseEntity<?> getTop5ProductSales(){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return dashboardService.getTop5ProductSale();
    }

    @GetMapping("/productInStock")
    public ResponseEntity<?> getProductInStock(){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return dashboardService.getTotalProduct();
    }
}
