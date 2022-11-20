package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import com.example.swp493_g1_camms.payload.request.WarehouseDTO;
import org.springframework.http.ResponseEntity;

public interface IWarehouseService {
    ResponseEntity<?> findAllWarehouse(int pageIndex, int pageSize) ;
    ResponseEntity<?> findWarehouseById(Long id);
    ResponseEntity<?> editWarehouse(WarehouseDTO warehouseDTO);
    ResponseEntity<?> addWarehouse(WarehouseDTO warehouseDTO);


}
