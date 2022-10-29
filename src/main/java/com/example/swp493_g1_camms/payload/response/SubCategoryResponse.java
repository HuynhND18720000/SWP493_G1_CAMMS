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
public class SubCategoryResponse {

    private Long id;

    private String name;

    private String description;

    private Long categoryId;

    public static List<SubCategoryResponse> createDataListSubCategoryByCatID(List<SubCategory> listSubCategory) {
        List<SubCategoryResponse> listSubCategoryResponses = new ArrayList<>();
        for (SubCategory subCategory : listSubCategory) {
            SubCategoryResponse subCategoryResponse = new SubCategoryResponse();
            subCategoryResponse.setId(subCategory.getId());
            subCategoryResponse.setName(subCategory.getName());
            subCategoryResponse.setDescription(subCategory.getDescription());
            subCategoryResponse.setCategoryId(subCategory.getCategory().getId());
            listSubCategoryResponses.add(subCategoryResponse);
        }
        return listSubCategoryResponses;
    }

}
