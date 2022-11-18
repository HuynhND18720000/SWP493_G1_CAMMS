package com.example.swp493_g1_camms.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "consignment_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentProduct{
    @ManyToOne
    @MapsId("consignment_id")
    @JoinColumn(name = "consignment_id")
    private Consignment consignment;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @EmbeddedId
    private ConsignmentProductKey id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

}
