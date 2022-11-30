package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentProductDTO {
    private Long consignmentId;

    private Long productId;

    private String expirationDate;

    private Integer quantity;

    private Double unitPrice;

    private Long warehouseId;

}
