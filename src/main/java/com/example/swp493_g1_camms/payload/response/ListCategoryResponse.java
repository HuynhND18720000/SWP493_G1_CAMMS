package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Category;
import com.example.swp493_g1_camms.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCategoryResponse {
    private Long id;

    private String name;

    private String description;

    private List<ListSubCategoryResponse> subCategory;

    public static List<ListCategoryResponse> createDataListCategory(List<Category> listCategory,
                                                                    List<SubCategory> listSubCategory) {
        List<ListCategoryResponse> listResponse = new ArrayList<>();
        for (Category category : listCategory) {
            List<SubCategory> list = new ArrayList<>();
            for (SubCategory subCategory : listSubCategory) {
                if(subCategory.getCategory().getId() == category.getId()) {
                    list.add(subCategory);
                }
            }
            ListCategoryResponse listCategoryResponse = new ListCategoryResponse();
            listCategoryResponse.setId(category.getId());
            listCategoryResponse.setName(category.getName());
            listCategoryResponse.setDescription(category.getDescription());
            listCategoryResponse.setSubCategory(ListSubCategoryResponse.createDataListSubCategory(list));
            listResponse.add(listCategoryResponse);
        }
        return listResponse;
    }
}
