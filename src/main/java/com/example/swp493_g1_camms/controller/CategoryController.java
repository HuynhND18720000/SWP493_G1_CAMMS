package com.example.swp493_g1_camms.controller;
import com.example.swp493_g1_camms.payload.request.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.swp493_g1_camms.services.interfaceService.ICategoryService;
@Controller
@RequestMapping("/api/category")
public class CategoryController {

    private final int defaultPage = 1;
    private final int defaultSize = 5;

    @Autowired
    ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> listCategory(@RequestParam(required = false) Integer pageIndex,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String categoryName,
                                          @RequestParam(required = false) String categoryDescription) {

        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;

        return categoryService.getAllCategory(pageIndex, pageSize, categoryName, categoryDescription);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> categoryDetail(@PathVariable Long categoryId,
                                            @RequestParam(required = false) Integer pageIndex,
                                            @RequestParam(required = false) Integer pageSize) {

        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return categoryService.findSubCategoryByCategoryId(categoryId, pageIndex, pageSize);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){

        return categoryService.addCategory(categoryDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO){

        return categoryService.updateCategory(categoryDTO);
    }

}

