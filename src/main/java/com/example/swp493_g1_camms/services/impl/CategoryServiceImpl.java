package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Category;
import com.example.swp493_g1_camms.entities.SubCategory;
import com.example.swp493_g1_camms.payload.request.CategoryDTO;
import com.example.swp493_g1_camms.payload.response.CategoryDetailResponse;
import com.example.swp493_g1_camms.payload.response.ListCategoryResponse;
import com.example.swp493_g1_camms.payload.response.ListSubCategoryResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.ICategoryRepository;
import com.example.swp493_g1_camms.repository.ISubCategoryRepository;
import com.example.swp493_g1_camms.services.interfaceService.ICategoryService;
import com.example.swp493_g1_camms.utils.ConvertToEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    ISubCategoryRepository subCategoryRepository;

    @Autowired
    ConvertToEntities convertToEntities;


    public CategoryServiceImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }
    @Override
    public ResponseEntity<?> getAllCategory(int pageIndex, int pageSize, String categoryName, String categoryDescription) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        String name = "";
        Page<Category> categoryPage = null;
        if (!ObjectUtils.isEmpty(categoryName)) {
            name = categoryName;
            categoryPage = categoryRepository.findBySearch(name.trim(), pageable);
        }else {
            categoryPage = categoryRepository.findAllByDeletedAt(false,pageable);
        }

        Map<String, Object> dataSearch = new HashMap<>();
        dataSearch.put("categoryName", categoryName);
        dataSearch.put("categoryDescription", categoryDescription);

        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        if (categoryPage.isEmpty()) {
            map.put("totalRecord", 0);
            map.put("category", categoryPage.getContent());
            responseVo.setMessage("Không tìm thấy List Category");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }

        List<SubCategory> listSubCategory = new ArrayList<>();
        listSubCategory = subCategoryRepository.findAll();
        map.put("category", ListCategoryResponse.createDataListCategory(categoryPage.getContent(), listSubCategory));
        map.put("searchData", dataSearch);
        map.put("currentPage", pageIndex);
        map.put("totalRecord", categoryPage.getTotalElements());
        map.put("pageSize", categoryPage.getSize());
        map.put("totalPage", categoryPage.getTotalPages());
        responseVo.setData(map);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findSubCategoryByCategoryId(Long categoryId, Integer pageIndex, Integer pageSize) {
        ResponseVo responseVo = new ResponseVo();
        if (!ObjectUtils.isEmpty(categoryId)) {
            Category category = categoryRepository.findCategoryById(categoryId);
            Map<String, Object> map = new HashMap<>();
            if (!ObjectUtils.isEmpty(category)) {
                Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
                Page<SubCategory> subCategoryPage = subCategoryRepository.findAllSubCategory(categoryId, pageable);
                if (subCategoryPage.isEmpty()) {
                    map.put("subCategory", subCategoryPage.getContent());
                    map.put("totalRecord", 0);
                    responseVo.setMessage("Không tìm thấy danh mục con");
                    responseVo.setData(map);
                }
                map.put("category", CategoryDetailResponse.createDataCategory(category));
                map.put("subCategory", ListSubCategoryResponse.createDataListSubCategory(subCategoryPage.getContent()));
                map.put("totalRecord", subCategoryPage.getTotalElements());
                map.put("curPage", pageIndex);
                map.put("pageSize", subCategoryPage.getSize());
                map.put("totalPage", subCategoryPage.getTotalPages());
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            responseVo.setMessage("không có sản phẩm nào");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> addCategory(CategoryDTO categoryDTO) {
        ResponseVo responseVo = new ResponseVo();
        Category c = categoryRepository.findCategoryByName(categoryDTO.getName().trim());
        if(c != null) {
            responseVo.setMessage("Danh mục đã bị tồn tại");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        SubCategory subCategory = subCategoryRepository.findSubCategoryByName(categoryDTO.getName().trim());
        if(subCategory != null) {
            responseVo.setMessage("Danh mục cha không được trùng với danh mục con");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        Category category = convertToEntities.convertCategory(categoryDTO);
        category.setDeletedAt(false);
        categoryRepository.save(category);
        responseVo.setMessage("Thêm danh mục thành công");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateCategory(CategoryDTO categoryDTO) {
        ResponseVo responseVo = new ResponseVo();
        Category c = categoryRepository.findCategoryByIdAndName(categoryDTO.getId(), categoryDTO.getName());
        if(c != null) {
            responseVo.setMessage("Danh mục đã bị tồn tại !!");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        SubCategory subCategory = subCategoryRepository.findSubCategoryByName(categoryDTO.getName().trim());
        if(subCategory != null) {
            responseVo.setMessage("Danh mục cha không được trùng với danh mục con");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        Category category = convertToEntities.convertCategory(categoryDTO);
        category.setDeletedAt(false);
        categoryRepository.save(category);
        responseVo.setMessage("Cập nhập thành công");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getAllCategoryNotPaging() {
        List<Category> listCategory = categoryRepository.getAllCategory();
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        if (listCategory.isEmpty()) {
            map.put("category", listCategory);
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy List Category");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<SubCategory> listSubCategory = new ArrayList<>();
        listSubCategory = subCategoryRepository.findAll();
        map.put("category", ListCategoryResponse.createDataListCategory(listCategory, listSubCategory));
        responseVo.setData(map);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
