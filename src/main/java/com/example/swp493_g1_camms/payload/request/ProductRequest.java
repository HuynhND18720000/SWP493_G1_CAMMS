package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private long id;
    private String productCode;
    private String name;
    private String description;
    private String image;
    private int quantity;
    private double unit_price;
    private String unit_measure;
    private Long category_id;
    private Long subCategory_id;
    private Long manufacturer_id;
    private LocalDateTime out_date;
    private String expiration_date;
    private String import_date;
    private Double lastAveragePrice;
}
