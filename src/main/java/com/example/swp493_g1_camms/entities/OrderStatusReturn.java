package com.example.swp493_g1_camms.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_status_return")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "status_id")
    private Boolean statusId;

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
