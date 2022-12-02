package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTakingHistoryDetailResponse {

    private Long id;

    private String userName;

    private String fullName;

    private String wareHouseName;

    private LocalDateTime createDate;

    private double totalDifferentAmount;

    private List<ListProductForCheckingDetailResponse> listProduct;

    public static StockTakingHistoryDetailResponse createSuccessData(
            StockTakingHistory stockTakingHistory,
            List<StockTakingHistoryDetail> listDetail,
            List<StockTakingHistoryDescription> listDescription,
            List<Product> listProduct, List<ConsignmentProduct> listConsignment) {
        StockTakingHistoryDetailResponse response = new StockTakingHistoryDetailResponse();
        response.setId(stockTakingHistory.getId());
        response.setUserName(stockTakingHistory.getUser().getUsername());
        response.setFullName(stockTakingHistory.getUser().getFullName());
        response.setWareHouseName(stockTakingHistory.getWarehouse().getName());
        response.setCreateDate(stockTakingHistory.getCreateDate());
        response.setTotalDifferentAmount(stockTakingHistory.getTotalDifferentAmount());
        response.setListProduct(ListProductForCheckingDetailResponse.createSuccessData(listProduct, listConsignment, listDetail, listDescription));
        return response;
    }
}
