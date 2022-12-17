package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.interfaceService.IExportOrderService;
import com.example.swp493_g1_camms.services.interfaceService.IReturnOderService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import com.example.swp493_g1_camms.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/return")
public class ReturnOrderController {
    private final int defaultPage = 1;

    private final int defaultSize = 5;
    @Autowired
    IReturnOderService returnOderImpl;

    @Autowired
    IExportOrderService exportOrder;

    @Autowired
    Validation validation;

    @PostMapping(path = "/createReturnOrder")
    public ResponseEntity<?> createReturnOrder( @RequestParam(required = false) Long orderId,
                                                @RequestParam(required = false) String orderCode,
                                                @RequestParam(required = false) Long confirmBy,
                                                @RequestParam(required = false) String description,
                                                @RequestBody List<ConsignmentProductDTO> consignmentProductDTOs){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return returnOderImpl.createReturnOrder(orderId, orderCode, confirmBy, description, consignmentProductDTOs);
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
    public ResponseEntity<ServiceResult<Map<String, Object>>> listReturn(@RequestParam(required = false) Integer pageIndex,
                                                                         @RequestParam(required = false) Integer pageSize,
                                                                         @RequestParam(required = false) String dateFrom,
                                                                         @RequestParam(required = false) String dateTo,
                                                                         @RequestParam(required = false) String orderCode
    ) throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            LocalDateTime dateFrom1 = null;
            LocalDateTime dateTo1 = null;
            dateFrom1 = validation.validDate(dateFrom1, dateFrom);
            dateTo1 = validation.validDate(dateTo1, dateTo);
            if(orderCode == null || orderCode.equalsIgnoreCase("") ){
                orderCode = "";
            }
            ServiceResult<Map<String, Object>> mapServiceResult = returnOderImpl.getListReturnOrders(pageIndex,
                    pageSize, dateFrom1, dateTo1, orderCode);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

