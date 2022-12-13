package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.*;
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

    private Double lastAveragePrice;
    private Integer quantity;

    private String unitMeasure;

    private Boolean deleted_at;

    private Category category;

    private SubCategory subCategory;

    private Manufacturer manufacturer;

    private Long categoryId;
    private Long subCategoryId;
    private Long manufactorId;
    private String categoryName;
    private String subcategoryName;
    private String manufactorName;
    public ProductResponse(Long id, Integer quantity){
        this.id = id;
        this.quantity = quantity;
    }

    public static ProductResponse createDetailProduct(Product product, ProductResponse productResponse){
        ProductResponse productResponse1 = new ProductResponse();
        productResponse1.setId(product.getId());
        productResponse1.setName(product.getName());
        productResponse1.setProductCode(product.getProductCode());
        productResponse1.setUnitMeasure(product.getUnitMeasure());
        productResponse1.setDescription(product.getDescription());
        productResponse1.setImage(product.getImage());
        productResponse1.setOut_date(product.getOutDate());
        productResponse1.setCategoryId(product.getCategory().getId());
        productResponse1.setManufactorId(product.getManufacturer().getId());
        productResponse1.setSubCategoryId(product.getSubCategory().getId());
        productResponse1.setQuantity(productResponse.getQuantity());
        productResponse1.setUnitprice(productResponse.getUnitprice());
        productResponse1.setCategoryName(product.getCategory().getName());
        productResponse1.setSubcategoryName(product.getSubCategory().getName());
        productResponse1.setManufactorName(product.getManufacturer().getName());
        productResponse1.setLastAveragePrice(product.getLastAveragePrice());
        return productResponse1;
    }

}
