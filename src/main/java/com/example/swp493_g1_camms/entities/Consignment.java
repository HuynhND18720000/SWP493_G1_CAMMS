package com.example.swp493_g1_camms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(name = "import_date")
    private LocalDateTime importDate;

    @Column(name = "consignment_code")
    private Long consignment_code;
    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StockTakingHistoryDetail> stockTakingHistoryDetails;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StockTakingHistoryDescription> stockTakingHistoryDescriptions;

    @OneToMany(mappedBy = "consignment")
    private Set<ConsignmentProduct> consignmentProducts;

    @OneToMany(mappedBy = "consignment")
    private Set<OrderStatusReturn> orderStatusReturns;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;



    @Override
    public String toString() {
        return "Consignment{" +
                "id=" + id +
                ", importDate=" + importDate +
                ", deletedAt=" + deletedAt +
                ", warehouse id=" + warehouse.getId() +
                '}';
    }
}
