package com.example.swp493_g1_camms.services.interfaceService;

import org.springframework.http.ResponseEntity;

public interface IDashboardService {
    ResponseEntity<?> getTotalProductInWarehouse();
    ResponseEntity<?> getTotalImportOrder();
    ResponseEntity<?> getTotalExportOrder();
    ResponseEntity<?> getTotalProduct();
    ResponseEntity<?> getTop5ProductSale();
}
