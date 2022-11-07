package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "unitprice")
    private Double unitprice;

    @Column(name = "out_date")
    private LocalDateTime outDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_measure")
    private String unitMeasure;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subCategory_id", referencedColumnName = "id")
    private SubCategory subCategory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "product")
    private Set<ConsignmentProduct> consignmentProducts;

    public Product(Long id, String productCode, String name, String description,
                   String image, Double unitprice,
                   LocalDateTime outDate, Integer quantity,
                   String unitMeasure, Boolean deletedAt) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.image = image;
        this.unitprice = unitprice;
        this.outDate = outDate;
        this.quantity = quantity;
        this.unitMeasure = unitMeasure;
        this.deletedAt = deletedAt;
    }
}
