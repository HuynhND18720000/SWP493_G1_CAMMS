package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailResponse {

    private Long id;

    private String name;

    private String description;

    public static CategoryDetailResponse createDataCategory(Category category) {
        CategoryDetailResponse categoryDetailResponse = new CategoryDetailResponse();
        categoryDetailResponse.setId(category.getId());
        categoryDetailResponse.setName(category.getName());
        categoryDetailResponse.setDescription(category.getDescription());
        return categoryDetailResponse;
    }

}