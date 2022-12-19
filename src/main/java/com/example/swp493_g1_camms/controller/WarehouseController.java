package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import com.example.swp493_g1_camms.payload.request.WarehouseDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import com.example.swp493_g1_camms.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.swp493_g1_camms.services.interfaceService.IWarehouseService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    private IWarehouseService IWarehouseService;
    @Autowired
    Validation validation;

    @GetMapping
    public ResponseEntity<?> getAllWarehouse(@RequestParam(required = false) Integer pageIndex,
                                             @RequestParam(required = false) Integer pageSize){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return IWarehouseService.findAllWarehouse(pageIndex, pageSize);
    }

    @GetMapping("/getAWarehouse")
    public ResponseEntity<?> getAWarehouse(@RequestParam(required = false) Long id){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return IWarehouseService.findWarehouseById(id);
    }

    @PutMapping("/editWarehouse")
    public ResponseEntity<?> editWarehouse(@RequestBody WarehouseDTO warehouseDTO){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return IWarehouseService.editWarehouse(warehouseDTO);
    }

    @PostMapping("/addWarehouse")
    public ResponseEntity<?> addWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        boolean checkWarehouseNameExist = validation.isWarehouseNameExist(warehouseDTO.getName());
        if(checkWarehouseNameExist == true){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("WarehouseName exist!", StatusUtils.NOT_Allow));
        }
        return IWarehouseService.addWarehouse(warehouseDTO);
    }
}
