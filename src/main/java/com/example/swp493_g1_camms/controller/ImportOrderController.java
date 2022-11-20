package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.ImportOrderImpl;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/import")
public class ImportOrderController {
    private final int defaultPage = 1;

    private final int defaultSize = 5;
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
    @GetMapping(path = "/getAllProductByManufacturer/{id}")
    public ResponseEntity<?> getAllProductByManufacturerId(@PathVariable Long id){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return importOrder.getProductByManufacturer(id);
    }
    @GetMapping("/list")
    public ResponseEntity<ServiceResult<Map<String, Object>>> listImport(@RequestParam(required = false) Integer pageIndex,
                                                                         @RequestParam(required = false) Integer pageSize) throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            ServiceResult<Map<String, Object>> mapServiceResult = importOrder.getListImportOrders(pageIndex, pageSize);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOrderDetail")
    public ResponseEntity<ServiceResult<Map<String, Object>>> getOrderDetail(@RequestParam(required = false) Integer pageIndex,
                                                                             @RequestParam(required = false) Integer pageSize,
                                                                             @RequestParam(required = false) Long orderId)
            throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            ServiceResult<Map<String, Object>> mapServiceResult = importOrder.getImportOderDetail(pageIndex, pageSize,orderId);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/confirm")
    public ResponseEntity<?> confirmOrder(@RequestParam(required = false) Long orderId,
                                          @RequestParam(required = false)Long confirmBy) {
        return importOrder.confirmOrder(orderId, confirmBy);
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam(required = false) Long orderId,
                                         @RequestParam(required = false)Long confirmBy) {
        return importOrder.cancelOrder(orderId, confirmBy);
    }

    @PutMapping("/editOrder")
    public ResponseEntity<?> editOrder(@RequestBody List<ConsignmentProductDTO> consignmentProductDTOList) {
        return importOrder.editOrder(consignmentProductDTOList);
    }
}
