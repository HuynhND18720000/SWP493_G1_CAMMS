package com.example.swp493_g1_camms.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "order_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted_at")
    private Boolean deletedAt;

    @OneToMany(mappedBy = "orderType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders;

    public OrderType(Long id, String description, String name, Boolean deletedAt) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.deletedAt = deletedAt;
    }
}
