package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.services.impl.ExportOrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/export")
public class ExportOrderController {
    private final int defaultPage = 1;

    private final int defaultSize = 5;
    @Autowired
    private ExportOrderImpl exportOrder;

    @GetMapping("/list")
    public ResponseEntity<ServiceResult<Map<String, Object>>> listImport(@RequestParam(required = false) Integer pageIndex,
                                                                         @RequestParam(required = false) Integer pageSize) throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            ServiceResult<Map<String, Object>> mapServiceResult = exportOrder.getListExportOrders(pageIndex, pageSize);
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
            ServiceResult<Map<String, Object>> mapServiceResult = exportOrder.getExportOderDetail(pageIndex, pageSize,orderId);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/confirm")
    public ResponseEntity<?> confirmOrder(@RequestParam(required = false) Long orderId,
                                          @RequestParam(required = false)Long confirmBy) {
        return exportOrder.confirmExportOrder(orderId, confirmBy);
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam(required = false) Long orderId,
                                         @RequestParam(required = false)Long confirmBy) {
        return exportOrder.cancelExportOrder(orderId, confirmBy);
    }

    @PutMapping("/editOrder")
    public ResponseEntity<?> editOrder(@RequestBody List<ConsignmentProductDTO> consignmentProductDTOList) {
        return exportOrder.editExportOrder(consignmentProductDTOList);
    }
}
