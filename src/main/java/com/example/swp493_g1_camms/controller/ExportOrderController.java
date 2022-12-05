package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.request.ExportOrderRequest;
import com.example.swp493_g1_camms.payload.request.OrderStatusExportedDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.ExportOrderServiceImpl;
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
@RequestMapping("/api/export")
public class ExportOrderController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    ExportOrderServiceImpl exportOrder;

    @GetMapping(path = "/listProduct")
    public ResponseEntity<?> loadListProductIntoDropList(){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return exportOrder.displayListProductInWarehouseToDropList();
    }

    @GetMapping(path = "/export-product")
    public ResponseEntity<?> getListProductFromDropdownList(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) Integer pageIndex,
                                                            @RequestParam(required = false) Integer pageSize){

        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;

        return exportOrder.getInfoProductInWareHouse(id, pageIndex,pageSize);
    }
    @PostMapping(path = "/create-exportOrder")
    public ResponseEntity<?> createExportOrder(@RequestBody ExportOrderRequest exportOrderRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return exportOrder.createExportOrder(exportOrderRequest);
    }

    @GetMapping
    public ResponseEntity<?> getListExportOrder(@RequestParam(required = false) Integer pageIndex,
                                                @RequestParam(required = false) Integer pageSize){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return exportOrder.getListExportOrder(pageIndex, pageSize);
    }
    @GetMapping("/list")
    public ResponseEntity<ServiceResult<Map<String, Object>>> listImport(@RequestParam(required = false) Integer pageIndex,
                                                                         @RequestParam(required = false) Integer pageSize,
                                                                         @RequestParam(required = false) Integer status,
                                                                         @RequestParam(required = false) String dateFrom,
                                                                         @RequestParam(required = false) String dateTo,
                                                                         @RequestParam(required = false) Long userId,
                                                                         @RequestParam(required = false) String orderCode
    ) throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            ServiceResult<Map<String, Object>> mapServiceResult =
                    exportOrder.getListExportOrders(pageIndex, pageSize, status, dateFrom, dateTo, userId, orderCode);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOrderDetail")
    public ResponseEntity<ServiceResult<Map<String, Object>>> getOrderDetail(@RequestParam(required = false) Long orderId)
            throws ParseException {
        try {
            ServiceResult<Map<String, Object>> mapServiceResult = exportOrder.getExportOderDetail(orderId);
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
    public ResponseEntity<?> editOrder(@RequestParam(required = false) Long orderId,
                                       @RequestBody List<ConsignmentProductDTO> consignmentProductDTOList) {
        return exportOrder.editExportOrder(orderId, consignmentProductDTOList);
    }

    //ok
    @PostMapping("/exported")
    public ResponseEntity<?> exportedExportOrder(@RequestParam(required = false) Long orderId
    ) {
        return exportOrder.exportedExportOrder(orderId);
    }

    //lay du lieu ra de xem va viet vao don huy
    @GetMapping("/getOrderDetailForCancelExportedExportOrder")
    public ResponseEntity<ServiceResult<Map<String, Object>>> getOrderDetailForCancelExportedExportOrder(@RequestParam(required = false) Long orderId)
            throws ParseException {
        try {
            ServiceResult<Map<String, Object>> mapServiceResult = exportOrder.getExportOderDetail(orderId);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancelExportedExportOrder")
    public ResponseEntity<?> cancelExportedExportOrder(@RequestParam(required = false) Long orderId,
                                                  @RequestBody List<OrderStatusExportedDTO> orderStatusExportedDTOS) {
        return exportOrder.cancelExportedExportOrder(orderId, orderStatusExportedDTOS);
    }

    @GetMapping("/getDetailCancelExportedExportOrder")
    public ResponseEntity<ServiceResult<Map<String, Object>>> getDetailCancelExportedExportOrder(@RequestParam(required = false) Long orderId)
            throws ParseException {
        try {
            ServiceResult<Map<String, Object>> mapServiceResult = exportOrder.getDetailCancelExportedExportOrder(orderId);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
