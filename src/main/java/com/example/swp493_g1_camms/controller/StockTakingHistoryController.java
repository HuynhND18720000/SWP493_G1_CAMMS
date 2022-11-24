package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.services.interfaceService.IStockTakingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;

        return stockTakingHistoryService.findAllStockTakingHistory(pageIndex, pageSize, wareHouseId, userId,
                startDate, endDate, orderBy);
    }


    @GetMapping("/detail/{stockTakingHistoryId}")
    public ResponseEntity<?> getStockTakingHistoryDetail(@PathVariable Long stockTakingHistoryId) {
        return stockTakingHistoryService.findListDetailById(stockTakingHistoryId);
    }



}
