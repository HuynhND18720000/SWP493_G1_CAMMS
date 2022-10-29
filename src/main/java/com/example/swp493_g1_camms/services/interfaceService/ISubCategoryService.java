package com.example.swp493_g1_camms.services.interfaceService;


import com.example.swp493_g1_camms.payload.request.SubCategoryDTO;
import org.springframework.http.ResponseEntity;

public interface ISubCategoryService {
    ResponseEntity<?> getSubCategoryByCateId(Long categoryId);

    ResponseEntity<?> addSubCategory(SubCategoryDTO subCategoryDTO);

    ResponseEntity<?> updateSubCategory(SubCategoryDTO subCategoryDTO);
}
