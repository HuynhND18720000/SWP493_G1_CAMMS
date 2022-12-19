package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.SubCategoryDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.interfaceService.ISubCategoryService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
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
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return subCategoryService.getSubCategoryByCateId(categoryId);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategoryDTO subCategoryDTO){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return subCategoryService.addSubCategory(subCategoryDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateSubCategory(@RequestBody SubCategoryDTO subCategoryRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return subCategoryService.updateSubCategory(subCategoryRequest);
    }
}
