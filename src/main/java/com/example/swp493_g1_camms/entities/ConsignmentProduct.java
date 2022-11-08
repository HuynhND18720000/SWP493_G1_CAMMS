package com.example.swp493_g1_camms.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consignment_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentProduct{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "consignment_id")
    private Consignment consignment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @Column(name = "unit_price")
    private Double unitPrice;


}
