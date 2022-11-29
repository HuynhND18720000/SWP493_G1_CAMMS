package com.example.swp493_g1_camms.payload.request;

import com.example.swp493_g1_camms.entities.Consignment;
import com.example.swp493_g1_camms.entities.Order;
import com.example.swp493_g1_camms.entities.Product;
import lombok.*;

import javax.persistence.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDeliverDTO {

    private String description;

    private Integer damagedQuantity;

    private Integer quantity;

    private Long consignmentId;

    private Long productId;

}