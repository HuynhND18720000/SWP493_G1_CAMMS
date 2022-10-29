package com.example.swp493_g1_camms.utils;

import com.example.swp493_g1_camms.entities.Category;
import com.example.swp493_g1_camms.entities.SubCategory;
import com.example.swp493_g1_camms.payload.request.CategoryDTO;
import com.example.swp493_g1_camms.payload.request.SubCategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class ConvertToEntities {
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
}