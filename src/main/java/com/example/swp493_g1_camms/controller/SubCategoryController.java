package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.SubCategoryDTO;
import com.example.swp493_g1_camms.services.interfaceService.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subCategory")
public class SubCategoryController {

    @Autowired
    ISubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<?> getSubCategoryByCategoryId(
            @RequestParam(required = false) Long categoryId) {
        return subCategoryService.getSubCategoryByCateId(categoryId);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategoryDTO subCategoryDTO){
        return subCategoryService.addSubCategory(subCategoryDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateSubCategory(@RequestBody SubCategoryDTO subCategoryRequest){

        return subCategoryService.updateSubCategory(subCategoryRequest);
    }
}
