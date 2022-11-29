package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "stock_taking_history_description")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTakingHistoryDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "difference_quantity")
    private Integer differentQuantity;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consignment_id", referencedColumnName = "id")
    private Consignment consignment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_taking_history_id", referencedColumnName = "id")
    private StockTakingHistory stockTakingHistory;
}
