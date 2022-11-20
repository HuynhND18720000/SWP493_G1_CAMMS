package com.example.swp493_g1_camms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

//annotation
@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;

     private String description;
     private Boolean deletedAt;

     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     @JsonIgnore
     private Set<Product> products;

     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private Set<SubCategory> subCategories;
}
