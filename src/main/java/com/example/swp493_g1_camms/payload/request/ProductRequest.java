package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private long Id;
    private String productCode;
    private String name;
    private String description;
    private String image;
    private double unit_price;
    private String unit_measure;
    private Long category_id;
    private Long subCategory_id;
    private Long manufacturer_id;
}
