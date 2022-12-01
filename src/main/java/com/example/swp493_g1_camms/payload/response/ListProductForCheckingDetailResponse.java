package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import com.example.swp493_g1_camms.entities.Product;
import com.example.swp493_g1_camms.entities.StockTakingHistoryDescription;
import com.example.swp493_g1_camms.entities.StockTakingHistoryDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ListProductForCheckingDetailResponse {
    private Long id;

    private String name;

    private String productCode;

    private String unitMeasure;

    private Integer quantity;

    private double unitPrice;

    private List<ListConsignmentForCheckingDetailResponse> listConsignment;

    public static List<ListProductForCheckingDetailResponse> createSuccessData(List<Product> listProduct,
                                                                               List<ConsignmentProduct> listConsignmentProduct, List<StockTakingHistoryDetail> listCheckingDetail, List<StockTakingHistoryDescription> listCheckingDescription) {
        List<ListProductForCheckingDetailResponse> listProductForCheckingDetailResponse = new ArrayList<>();
        for (Product product : listProduct) {
            ListProductForCheckingDetailResponse response = new ListProductForCheckingDetailResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setProductCode(product.getProductCode());
            response.setUnitMeasure(product.getUnitMeasure());
            response.setQuantity(product.getQuantity());
            response.setUnitPrice(product.getUnitprice());
            response.setListConsignment(ListConsignmentForCheckingDetailResponse.
                    createSuccessData(product.getId(), listConsignmentProduct, listCheckingDetail, listCheckingDescription));
            listProductForCheckingDetailResponse.add(response);
        }
        return listProductForCheckingDetailResponse;
    }
}
