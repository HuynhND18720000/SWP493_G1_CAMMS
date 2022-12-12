package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.StockTakingRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.StockTakingHistoryServiceImpl;
import com.example.swp493_g1_camms.services.interfaceService.IStockTakingHistoryService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stockTakingHistory")
public class StockTakingHistoryController {

    private final int defaultPage = 1;
    private final int defaultSize = 5;


    @Autowired
    IStockTakingHistoryService stockTakingHistoryService;


    @GetMapping
    public ResponseEntity<?> listStockTakingHistory(@RequestParam(required = false) Integer pageIndex,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) Long wareHouseId,
                                                    @RequestParam(required = false) Long userId,
                                                    @RequestParam(required = false) String startDate,
                                                    @RequestParam(required = false) String endDate,
                                                    @RequestParam(required = false) String orderBy) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;

        return stockTakingHistoryService.findAllStockTakingHistory(pageIndex, pageSize, wareHouseId, userId,
                startDate, endDate, orderBy);
    }

    @PutMapping("/createStockTakingHistory")
    public ResponseEntity<?> createStockTakingHistory(@RequestBody StockTakingRequest stockTakingRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return stockTakingHistoryService.createStockTakingHistory(stockTakingRequest);
    }

    @GetMapping("/detail/{stockTakingHistoryId}")
    public ResponseEntity<?> getStockTakingHistoryDetail(@PathVariable Long stockTakingHistoryId) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return stockTakingHistoryService.findListDetailById(stockTakingHistoryId);
    }

    @GetMapping("/productByWarehouse")
    public ResponseEntity<?> getProductByWarehouse(@RequestParam(required = false) Long warehouse_id){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return stockTakingHistoryService.getProducFromConsignmentInWarehouse(warehouse_id);
    }

    @GetMapping(path = "/productDetails")
    public ResponseEntity<?> getListProductFromDropdownList(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) Long wid){

        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }

        return stockTakingHistoryService.getInfoProductInWareHouse(id, wid);
    }

}
