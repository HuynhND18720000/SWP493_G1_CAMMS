package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "return_to_manufacturer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnToManufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirmed_date")
    private LocalDateTime confirmedDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "expected_return_date")
    private LocalDateTime expectedReturnDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "user_confirmed_id")
    private Long userConfirmedId;


    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "returnToManufacturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReturnToManufacturerDetail> returnToManufacturerDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_create_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

}
