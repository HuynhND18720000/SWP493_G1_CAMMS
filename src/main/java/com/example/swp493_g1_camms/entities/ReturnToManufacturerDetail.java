package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "return_to_manufacturer_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnToManufacturerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_measure")
    private String unitMeasure;

    @Column(name = "unit_price")
    private Double unitPrice;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "return_to_manufacturer_id", referencedColumnName = "id")
    private ReturnToManufacturer returnToManufacturer;


}
