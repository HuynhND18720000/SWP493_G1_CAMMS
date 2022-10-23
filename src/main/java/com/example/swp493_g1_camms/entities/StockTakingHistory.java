package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "stock_taking_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTakingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @Column(name = "total_different_amount")
    private Double totalDifferentAmount;

    @OneToMany(mappedBy = "stockTakingHistory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StockTakingHistoryDetail> stockTakingHistoryDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;
}
