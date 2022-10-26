package com.example.swp493_g1_camms.entities;

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
     private long id;
     @Column(name = "name")
     private String name;
     @Column(name = "description")
     private String description;
     @Column(name = "deleted_at")
     private boolean deleted_at;
}
