package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Consignment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "import_date")
    private LocalDateTime importDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StockTakingHistoryDetail> stockTakingHistoryDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    public Consignment(Long id, LocalDateTime expirationDate,
                       LocalDateTime importDate, Integer quantity, Double unitPrice) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.importDate = importDate;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    public Consignment(Long id, LocalDateTime expirationDate, LocalDateTime importDate,
                       Integer quantity, Double unitPrice, Product product) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.importDate = importDate;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product=product;
    }

}
