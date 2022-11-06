package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.entities.Product;
import com.example.swp493_g1_camms.payload.response.ListProductResponse;
import com.example.swp493_g1_camms.payload.response.ProductResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    IConsignmentRepository consignmentRepository;
    @Autowired
    ISubCategoryRepository subCategoryRepository;
    @Autowired
    IManufacturerRepository manufacturerRepository;
    //lay toan bo san pham trong kho va phan trang
    @Override
    public ResponseEntity<?> getAllProducts(int pageIndex, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
            String name = "";
            Page<Product> productListPage = null;
            ResponseVo responseVo = new ResponseVo();
            Map<String, Object> map = new HashMap<>();
            productListPage = productRepository.findAllProductsBydeletedAt(false, pageable);

            if(productListPage.isEmpty()){
                map.put("product",productListPage.getContent());
                map.put("totalRecord", 0);
                responseVo.setMessage("Không tìm thấy danh sách khách hàng!");
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            map.put("product", ListProductResponse.createSuccessData(productListPage.getContent()));
            map.put("currentPage", pageIndex);
            map.put("pageSize", pageSize);
            map.put("totalPage", productListPage.getTotalPages());
            responseVo.setData(map);
            responseVo.setMessage("Lay du lieu thanh cong");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findAllProduct(int pageIndex, int pageSize, String productName,
                                            String productCode, Long categoryId, Long manufactorId) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        String productSearch = "";
        Page<Product> productPage = null;
        if(!ObjectUtils.isEmpty(productName)){
            productSearch = productName;
            productPage =  productRepository.findBySearch(productSearch.trim(),
                    categoryId, manufactorId, pageable);
        }else{
            productPage = productRepository.findAllBySearch(categoryId, manufactorId,pageable);
        }

        Map<String, Object> mapSearch = new HashMap<>();
        mapSearch.put("productName", productName);
        mapSearch.put("productCode", productCode);
        mapSearch.put("categoryId", categoryId);
        mapSearch.put("manufactorId", manufactorId);
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        if(productPage.isEmpty()){
            map.put("product",productPage.getContent());
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy danh sách khách hàng!");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<ProductResponse> listProductResponse = new ArrayList<>();
        for (Product p:productPage
             ) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(p.getId());
            productResponse.setQuantity(consignmentRepository.countQuantity(p.getId()));
            listProductResponse.add(productResponse);
        }
        map.put("product", ListProductResponse.createProductData(productPage.getContent(),
                listProductResponse));
        map.put("searchData", mapSearch);
        map.put("currentPage", pageIndex);
        map.put("totalRecord", productPage.getTotalElements());
        map.put("pageSize", productPage.getSize());
        map.put("totalPage", productPage.getTotalPages());
        responseVo.setData(map);
        return  new ResponseEntity<>(responseVo, HttpStatus.OK);
    }


}
