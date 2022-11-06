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
    private double unitprice;
    private Date out_date;
    private int quantity;
    private String unit_measure;
    private boolean deletedAt;
    private Long category_id;
    private Long subCategory_id;
    private Long manufacturor_id;
}
