package com.example.swp493_g1_camms.payload.response;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Top5ProductSaleResponse {
    private String productCode;
    private int totalSales;
}
