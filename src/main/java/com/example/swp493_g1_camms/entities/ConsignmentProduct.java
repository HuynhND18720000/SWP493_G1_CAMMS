package com.example.swp493_g1_camms.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Consignment_Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "consignment_id")
    private Consignment consignment;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "deleted_at")
    private Boolean deletedAt;


}
