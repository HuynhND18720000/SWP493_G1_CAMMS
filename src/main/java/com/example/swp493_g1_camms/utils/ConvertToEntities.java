package com.example.swp493_g1_camms.utils;

import com.example.swp493_g1_camms.entities.Category;
import com.example.swp493_g1_camms.entities.Product;
import com.example.swp493_g1_camms.entities.SubCategory;
import com.example.swp493_g1_camms.payload.request.CategoryDTO;
import com.example.swp493_g1_camms.payload.request.ProductRequest;
import com.example.swp493_g1_camms.payload.request.SubCategoryDTO;
import com.example.swp493_g1_camms.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ConvertToEntities {
    @Autowired
    IProductRepository IProductRepository;
    public SubCategory convertSubCategory(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryDTO.getId());
        subCategory.setName(subCategoryDTO.getName().trim());
        subCategory.setDescription(subCategoryDTO.getDescription());
        return subCategory;
    }
    public Category convertCategory(CategoryDTO CategoryDTO) {
        Category category = new Category();
        category.setId(CategoryDTO.getId());
        category.setName(CategoryDTO.getName().trim());
        category.setDescription(CategoryDTO.getDescription());
        return category;
    }

    public Product convertProduct(ProductRequest productRequest){
        Product product = new Product();
        //cho nay nen luu y id cua product boi vi khi add thi ko co id
        //thi co nen de set id hay cho nay de id tu tang
        product.setId(productRequest.getId());
        product.setName(productRequest.getName().trim());
        product.setProductCode(productRequest.getProductCode());
        product.setUnitMeasure(productRequest.getUnit_measure());
        product.setDescription(productRequest.getDescription());
        product.setUnitprice(productRequest.getUnit_price());
        if(productRequest.getImage() != null) {
            product.setImage(productRequest.getImage());
        }
        return product;
    }

    public Product convertProductToAddConsignmentProduct(ProductRequest productRequest){
        Product product = new Product();
        product.setId(productRequest.getId());
        product.setName(productRequest.getName());
        product.setProductCode(productRequest.getProductCode());
        product.setQuantity(productRequest.getQuantity());
        product.setUnitMeasure(productRequest.getUnit_measure());
        product.setDescription(productRequest.getDescription());
        product.setUnitprice(productRequest.getUnit_price());
        String expiration_date = productRequest.getExpiration_date();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(expiration_date, formatter);
        product.setOutDate(dateTime);
        product.setDeletedAt(false);
        if(productRequest.getImage() != null) {
            product.setImage(productRequest.getImage());
        }
        return product;
    }

}