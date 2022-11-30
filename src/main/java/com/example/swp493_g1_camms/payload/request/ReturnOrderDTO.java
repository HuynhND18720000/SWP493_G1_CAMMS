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
public class ReturnOrderDTO {
    private Long warehouseId;
    private Long orderId;
    private String orderCode;
    private Long confirmBy;
    private String description;
    List<ConsignmentProductDTO> consignmentProductDTOs;
}
