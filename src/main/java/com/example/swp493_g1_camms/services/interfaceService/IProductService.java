package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.Product;
import com.example.swp493_g1_camms.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {

    ResponseEntity<?> getAllProducts(int pageIndex, int pageSize);
    ResponseEntity<?> findAllProduct(int pageIndex, int pageSize, String productName, String productCode,
                              Long categoryId, Long manufactorId);
    ResponseEntity<?> findById(Long productId,int pageIndex, int pageSize);
    ResponseEntity<?> addProduct(ProductRequest productRequest);
    ResponseEntity<?> updateProduct(ProductRequest productRequest);
    ResponseEntity<?> deleteProductById(Long productId);
}
