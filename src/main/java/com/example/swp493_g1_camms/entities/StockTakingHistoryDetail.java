package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "stock_taking_history_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTakingHistoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deviant_amount")
    private Double deviantAmount;

    @Column(name = "instock_quantity")
    private Integer instockQuantity;

    @Column(name = "reality_quantity")
    private Integer realityQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consignment_id", referencedColumnName = "id")
    private Consignment consignment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_taking_history_id", referencedColumnName = "id")
    private StockTakingHistory stockTakingHistory;


}
