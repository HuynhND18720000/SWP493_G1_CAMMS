package com.example.swp493_g1_camms.payload.response;


import com.example.swp493_g1_camms.entities.Consignment;
import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import com.example.swp493_g1_camms.entities.StockTakingHistoryDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListConsignmentForCheckingDetailResponse {

    private Long id;

    private LocalDateTime expirationDate;

    private LocalDateTime importDate;

    private Double unitPrice;

    private Integer instockQuantity;

    private Integer realityQuantity;

    private double deviantAmount;

    public static List<ListConsignmentForCheckingDetailResponse> createSuccessData(Long productId,
                                                                                   List<ConsignmentProduct> listConsignmentProduct, List<StockTakingHistoryDetail> listCheckingDetail) {
        List<ListConsignmentForCheckingDetailResponse> listConsignmentForCheckingHistoryResponse = new ArrayList<>();
        for (ConsignmentProduct consignment : listConsignmentProduct) {
            for (StockTakingHistoryDetail stockTakingHistoryDetail : listCheckingDetail) {
                if (consignment.getProduct().getId() == productId &&
                        stockTakingHistoryDetail.getConsignment().getId() == consignment.getConsignment().getId()) {
                    ListConsignmentForCheckingDetailResponse response = new ListConsignmentForCheckingDetailResponse();
                    response.setId(consignment.getConsignment().getId());
                    response.setExpirationDate(consignment.getExpirationDate());
                    response.setImportDate(consignment.getConsignment().getImportDate());
                    response.setUnitPrice(consignment.getUnitPrice());
                    response.setInstockQuantity(stockTakingHistoryDetail.getInstockQuantity());
                    response.setRealityQuantity(stockTakingHistoryDetail.getRealityQuantity());
                    response.setDeviantAmount(stockTakingHistoryDetail.getDeviantAmount());
                    listConsignmentForCheckingHistoryResponse.add(response);
                }
            }
        }
        return listConsignmentForCheckingHistoryResponse;
    }


}
