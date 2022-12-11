package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImportOrderRequest {
    private Long manufacturerId;
    private Long warehouseId;
    private Long user_Id;
    private ConsignmentRequest consignmentRequest;

}
