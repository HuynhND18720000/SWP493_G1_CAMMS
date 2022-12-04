package com.example.swp493_g1_camms.payload.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusExportedDTO {

    private String description;

    private Integer damagedQuantity;

    private Integer quantity;

    private Long consignmentId;

    private Long productId;

}