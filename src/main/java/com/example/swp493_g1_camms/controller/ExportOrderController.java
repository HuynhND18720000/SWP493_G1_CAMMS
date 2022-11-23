package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ExportOrderRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.ExportOrderImpl;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
public class ExportOrderController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    ExportOrderImpl exportOrder;
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
}
