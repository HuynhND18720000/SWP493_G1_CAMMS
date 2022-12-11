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
    @MapsId("consignmentid")
    @JoinColumn(name = "consignment_id")
    private Consignment consignment;

    @ManyToOne
    @MapsId("productid")
    @JoinColumn(name = "product_id")
    private Product product;

    @EmbeddedId
    private ConsignmentProductKey id = new ConsignmentProductKey();

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "mark_con_id")
    private Long mark_get_product_from_consignment;

    @Column(name = "quantity_sale")
    private Integer quantity_sale;

    @Column(name="average_price")
    private Double average_price;

    @Column(name = "import_date")
    private LocalDateTime import_date;
}
