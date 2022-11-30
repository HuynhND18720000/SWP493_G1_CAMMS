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
    private Long consignmentIdFrom;

    private Long productId;

    private String expirationDate;

    private Integer quantityReturn;

    private Double unitPrice;

    private Long warehouseIdFrom;

}
