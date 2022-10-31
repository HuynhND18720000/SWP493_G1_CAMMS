package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {

    ResponseEntity<?> getAllCategory(int pageIndex, int pageSize,
                                     String categoryName, String categoryDescription);

    ResponseEntity<?> findSubCategoryByCategoryId(Long categoryId, Integer pageIndex, Integer pageSize);

    ResponseEntity<?> addCategory(CategoryDTO categoryDTO);

    ResponseEntity<?> updateCategory(CategoryDTO categoryDTO);

    ResponseEntity<?> getAllCategoryNotPaging();

}