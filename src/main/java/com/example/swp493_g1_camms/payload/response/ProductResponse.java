package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Category;
import com.example.swp493_g1_camms.entities.Consignment;
import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.entities.SubCategory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;

    private String productCode;

    private String name;

    private String description;

    private String image;

    private Double unitprice;

    private LocalDateTime out_date;

    private Integer quantity;

    private String unitMeasure;

    private Boolean deleted_at;

    private Category category;

    private SubCategory subCategory;

    private Manufacturer manufacturer;

    public ProductResponse(Long id, Integer quantity){
        this.id = id;
        this.quantity = quantity;
    }

}
