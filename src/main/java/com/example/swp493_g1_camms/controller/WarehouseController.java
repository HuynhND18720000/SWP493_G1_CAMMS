package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.WarehouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.swp493_g1_camms.services.interfaceService.IWarehouseService;


@RestController
public class WarehouseController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    private IWarehouseService IWarehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<?> getAllWarehouse(@RequestParam(required = false) Integer pageIndex,
                                             @RequestParam(required = false) Integer pageSize){
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return IWarehouseService.findAllWarehouse(pageIndex, pageSize);
    }

    @GetMapping("/getAWarehouse")
    public ResponseEntity<?> getAWarehouse(@RequestParam(required = false) Long id){
        return IWarehouseService.findWarehouseById(id);
    }

    @PutMapping("/editWarehouse")
    public ResponseEntity<?> editWarehouse(@RequestBody WarehouseDTO warehouseDTO){
        return IWarehouseService.editWarehouse(warehouseDTO);
    }
}
