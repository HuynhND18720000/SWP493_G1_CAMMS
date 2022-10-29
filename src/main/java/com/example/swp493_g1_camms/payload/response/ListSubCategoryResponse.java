package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListSubCategoryResponse {
    private Long id;

    private String name;

    private String description;

    private String categoryName;

    private Long categoryId;

    public static List<ListSubCategoryResponse> createDataListSubCategory(List<SubCategory> listSubCategory) {
        List<ListSubCategoryResponse> listResponse = new ArrayList<>();
        for (SubCategory subCategory : listSubCategory) {
            ListSubCategoryResponse listSubCategoryResponse = new ListSubCategoryResponse();
            listSubCategoryResponse.setId(subCategory.getId());
            listSubCategoryResponse.setName(subCategory.getName());
            listSubCategoryResponse.setDescription(subCategory.getDescription());
            listSubCategoryResponse.setCategoryName(subCategory.getCategory().getName());
            listSubCategoryResponse.setCategoryId(subCategory.getCategory().getId());
            listResponse.add(listSubCategoryResponse);
        }
        return listResponse;
    }
}
