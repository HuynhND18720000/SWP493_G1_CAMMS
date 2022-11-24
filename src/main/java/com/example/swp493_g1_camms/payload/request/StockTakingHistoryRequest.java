package com.example.swp493_g1_camms.payload.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockTakingHistoryRequest {
    private Long warehouse_Id;
    private List<ProductStockTakingRequest> productStockTakingRequestList;
}
