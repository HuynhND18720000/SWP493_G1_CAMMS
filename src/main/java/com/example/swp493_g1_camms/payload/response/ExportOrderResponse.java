package com.example.swp493_g1_camms.payload.response;

import lombok.*;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExportOrderResponse {
    private Long productId;

    private String productCode;

    private String productName;

    private String unitMeasure;

    private double unitPrice;

    private int quantity;
    private Double importPrice;
    List<ListConsignmentProductResponse> consignmentList;
}
