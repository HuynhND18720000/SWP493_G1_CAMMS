package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.StockTakingRequest;
import org.springframework.http.ResponseEntity;

public interface IStockTakingHistoryService {

    ResponseEntity<?> findListDetailById(Long stockTakingHistoryId);

    ResponseEntity<?> findAllStockTakingHistory(Integer pageIndex, Integer pageSize, Long wareHouseId, Long userId,
                              String startDate, String endDate, String orderBy);

    ResponseEntity<?> createStockTakingHistory(StockTakingRequest stockTakingRequest);

    ResponseEntity<?> getProducFromConsignmentInWarehouse(Long warehouse_id);

    public ResponseEntity<?> getInfoProductInWareHouse(Long product_id, Long warehouse_id);
}
