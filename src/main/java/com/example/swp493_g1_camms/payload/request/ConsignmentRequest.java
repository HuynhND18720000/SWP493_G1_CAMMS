package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentRequest {
    private Long consignmentId;
    private LocalDateTime expirationDate;
    private LocalDateTime importDate;
    private double unit_price;
    private int quantity;
    private List<ProductRequest> productRequestList;
}
