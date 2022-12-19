package com.example.swp493_g1_camms.controller;
import com.example.swp493_g1_camms.payload.request.CategoryDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.swp493_g1_camms.services.interfaceService.ICategoryService;
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
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
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;

        return categoryService.getAllCategory(pageIndex, pageSize, categoryName, categoryDescription);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> categoryDetail(@PathVariable Long categoryId,
                                            @RequestParam(required = false) Integer pageIndex,
                                            @RequestParam(required = false) Integer pageSize) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return categoryService.findSubCategoryByCategoryId(categoryId, pageIndex, pageSize);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return categoryService.addCategory(categoryDTO);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return categoryService.updateCategory(categoryDTO);
    }
    @GetMapping("/notPaging")
    public ResponseEntity<?> listCategoryNotPaging() {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return categoryService.getAllCategoryNotPaging();
    }

}

