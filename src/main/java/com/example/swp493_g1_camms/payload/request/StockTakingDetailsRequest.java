package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTakingDetailsRequest {
    //consignment_id
    private Long id;

    private Long productId;

    private Integer quantityInstock;

    private Integer quantity;
    //diviant amount
    private double differentAmout;

    private String description;

    private Double unitPrice;
}
