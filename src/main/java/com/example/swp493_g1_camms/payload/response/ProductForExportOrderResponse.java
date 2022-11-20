package com.example.swp493_g1_camms.payload.response;

import lombok.*;

import java.math.BigInteger;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ProductForExportOrderResponse {
    private Long id;

    private Long warehouseId;

    private String warehouseName;

    private String importDate;

    private String expirationDate;

    private Integer quantityInstock;

    private Integer quantity;
//    private Double unit_price;
//    private String name;
//    private String code;
}
