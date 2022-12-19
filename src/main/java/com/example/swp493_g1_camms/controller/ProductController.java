package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ProductRequest;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.impl.ProductServiceImpl;
import com.example.swp493_g1_camms.services.interfaceService.IProductService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/products")
public class ProductController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    IProductService productService;
    //cách 1: get all products
    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false) Integer pageIndex,
                                            @RequestParam(required = false) Integer pageSize){
        boolean currentUserIsActive = CurrentUserIsActive.currentUserIsActive();
        if(!currentUserIsActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Tài khoản của bạn đã bị tạm dừng!", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return productService.getAllProducts(pageIndex, pageSize);
    }

    //cách 2: get all product
    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getListProducts(@RequestParam(required = false) Integer pageIndex,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String productName,
                                             @RequestParam(required = false) String productCode,
                                             @RequestParam(required = false) Long manufactorId,
                                             @RequestParam(required = false) Long categoryId){

        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return productService.findAllProduct(pageIndex,pageSize,productName,productCode,categoryId,
                manufactorId);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> productDetail(
            @PathVariable Long productId,
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
        return productService.findById(productId, pageIndex, pageSize);
    }

    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return productService.addProduct(productRequest);
    }

    @PutMapping(path = "/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return productService.updateProduct(productRequest);
    }

    @DeleteMapping(path = "/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return productService.deleteProductById(productId);
    }
}
