package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ReturnOrderDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.ExportOrderImpl;
import com.example.swp493_g1_camms.services.impl.ReturnOrderImpl;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@Controller
@RequestMapping("/api/return")
public class ReturnOrderController {
    private final int defaultPage = 1;

    private final int defaultSize = 5;
    @Autowired
    ReturnOrderImpl returnOderImpl;

    @Autowired
    ExportOrderImpl exportOrder;

    @PostMapping(path = "/createReturnOrder")
    public ResponseEntity<?> createReturnOrder(@RequestBody ReturnOrderDTO returnOrderDTO){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return returnOderImpl.createReturnOrder(returnOrderDTO);
    }

    @GetMapping("/getOrderDetail")
    public ResponseEntity<ServiceResult<Map<String, Object>>> getOrderDetail(@RequestParam(required = false) Long orderId)
            throws ParseException {
        try {
            ServiceResult<Map<String, Object>> mapServiceResult = exportOrder.getOderDetail(orderId);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ServiceResult<Map<String, Object>>> listImport(@RequestParam(required = false) Integer pageIndex,
                                                                         @RequestParam(required = false) Integer pageSize) throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            ServiceResult<Map<String, Object>> mapServiceResult = returnOderImpl.getListReturnOrders(pageIndex, pageSize);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
