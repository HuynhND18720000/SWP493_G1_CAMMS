package com.example.swp493_g1_camms.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_exported")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderExported {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status_exported")
    private Boolean statusExported;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "damaged_quantity")
    private Integer damagedQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consignment_id", referencedColumnName = "id")
    private Consignment consignment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

}
