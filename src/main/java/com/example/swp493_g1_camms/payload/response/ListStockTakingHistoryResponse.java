package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.StockTakingHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListStockTakingHistoryResponse {
    private Long id;

    private LocalDateTime createDate;

    private String userName;

    private String fullName;

    private String wareHouseName;

    private double differentAmount;

    private Long userId;

    private Long wareHouseId;

    public static List<ListStockTakingHistoryResponse> createListStockTakingHistory(
            List<StockTakingHistory> listStockTakingHistory) {
        List<ListStockTakingHistoryResponse> response = new ArrayList<>();
        for (StockTakingHistory stockTakingHistory : listStockTakingHistory) {
            ListStockTakingHistoryResponse listStockTakingHistoryResponse = new ListStockTakingHistoryResponse();
            listStockTakingHistoryResponse.setId(stockTakingHistory.getId());
            listStockTakingHistoryResponse.setCreateDate(stockTakingHistory.getCreateDate());
            listStockTakingHistoryResponse.setUserName(stockTakingHistory.getUser().getUsername());
            listStockTakingHistoryResponse.setFullName(stockTakingHistory.getUser().getFullName());
            listStockTakingHistoryResponse.setWareHouseName(stockTakingHistory.getWarehouse().getName());
            listStockTakingHistoryResponse.setDifferentAmount(stockTakingHistory.getTotalDifferentAmount());
            listStockTakingHistoryResponse.setUserId(stockTakingHistory.getUser().getId());
            listStockTakingHistoryResponse.setWareHouseId(stockTakingHistory.getWarehouse().getId());
            response.add(listStockTakingHistoryResponse);
        }
        return response;
    }
}
