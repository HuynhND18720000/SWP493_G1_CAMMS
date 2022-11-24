package com.example.swp493_g1_camms.payload.request;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockTakingRequest {
    private Long product_Id;
    private String product_name;
    private String unit_measure;
    private double unit_price;
    private List<ListConsignmentProductForCheckingRequest> listConsignmentProductForCheckingRequestList;
}
