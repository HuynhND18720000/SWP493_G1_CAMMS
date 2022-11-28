package com.example.swp493_g1_camms.payload.request;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockTakingRequest {
    private Long user_Id;

    private Long warehouse_Id;

    private double total_Different_Amout;

    private List<StockTakingDetailsRequest> list_StockTakingDetails;
}
