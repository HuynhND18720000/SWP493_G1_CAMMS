package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import org.springframework.http.ResponseEntity;

public interface IImportOrderService {
    ResponseEntity<?> createOrder(ImportOrderRequest importOrderRequest);
    ResponseEntity<?> getProductByManufacturer(Long id);
}
