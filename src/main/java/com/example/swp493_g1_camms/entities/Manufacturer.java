package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "manufacturer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReturnToManufacturer> returnToManufacturers;

}
