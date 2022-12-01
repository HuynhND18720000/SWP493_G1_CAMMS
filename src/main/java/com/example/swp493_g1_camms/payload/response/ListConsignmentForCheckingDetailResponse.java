package com.example.swp493_g1_camms.payload.response;


import com.example.swp493_g1_camms.entities.Consignment;
import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import com.example.swp493_g1_camms.entities.StockTakingHistoryDescription;
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

    private String descriptions;

    private Integer different_quantity;
    public static List<ListConsignmentForCheckingDetailResponse> createSuccessData(Long productId,
                                                                                   List<ConsignmentProduct> listConsignmentProduct, List<StockTakingHistoryDetail> listCheckingDetail, List<StockTakingHistoryDescription> listCheckingDescription) {
        List<ListConsignmentForCheckingDetailResponse> listConsignmentForCheckingHistoryResponse = new ArrayList<>();
        for (ConsignmentProduct consignment : listConsignmentProduct) {
            for (StockTakingHistoryDetail stockTakingHistoryDetail : listCheckingDetail) {
                for(StockTakingHistoryDescription stockTakingHistoryDescription : listCheckingDescription){
                    if (consignment.getProduct().getId() == productId && stockTakingHistoryDetail.getConsignment().getId() == consignment.getConsignment().getId()
                        && stockTakingHistoryDescription.getConsignment().getId() == consignment.getConsignment().getId()) {
                        ListConsignmentForCheckingDetailResponse response = new ListConsignmentForCheckingDetailResponse();
                        response.setId(consignment.getConsignment().getId());
                        response.setExpirationDate(consignment.getExpirationDate());
                        response.setImportDate(consignment.getConsignment().getImportDate());
                        response.setUnitPrice(consignment.getUnitPrice());
                        response.setInstockQuantity(stockTakingHistoryDetail.getInstockQuantity());
                        response.setRealityQuantity(stockTakingHistoryDetail.getRealityQuantity());
                        response.setDeviantAmount(stockTakingHistoryDetail.getDeviantAmount());
                        response.setDifferent_quantity(stockTakingHistoryDescription.getDifferentQuantity());
                        response.setDescriptions(stockTakingHistoryDescription.getDescription());
                        listConsignmentForCheckingHistoryResponse.add(response);
                }
                }
            }
        }
        return listConsignmentForCheckingHistoryResponse;
    }


}
