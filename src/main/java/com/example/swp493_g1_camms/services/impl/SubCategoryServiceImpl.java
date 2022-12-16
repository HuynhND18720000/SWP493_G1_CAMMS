package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Category;
import com.example.swp493_g1_camms.entities.SubCategory;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.payload.request.SubCategoryDTO;
import com.example.swp493_g1_camms.payload.response.SubCategoryResponse;
import com.example.swp493_g1_camms.repository.ICategoryRepository;
import com.example.swp493_g1_camms.repository.ISubCategoryRepository;
import com.example.swp493_g1_camms.services.interfaceService.ISubCategoryService;
import com.example.swp493_g1_camms.utils.ConvertToEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SubCategoryServiceImpl implements ISubCategoryService {

    @Autowired
    ISubCategoryRepository subCategoryRepository;

    @Autowired
    ConvertToEntities convertToEntities;

    @Autowired
    ICategoryRepository categoryRepository;


    @Override
    public ResponseEntity<?> getSubCategoryByCateId(Long categoryId) {
        ResponseVo responseVo = new ResponseVo();
        if (!ObjectUtils.isEmpty(categoryId)) {
            List<SubCategory> listSubCategory = subCategoryRepository.getSubCategoryByCateId(categoryId);
            Map<String, Object> map = new HashMap<>();
            if (!ObjectUtils.isEmpty(listSubCategory)) {
                map.put("subCategory",
                        SubCategoryResponse.createDataListSubCategoryByCatID(listSubCategory));
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            responseVo.setMessage("Danh mục con không tồn tại");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> addSubCategory(SubCategoryDTO subCategoryDTO) {
        ResponseVo responseVo = new ResponseVo();
        SubCategory subCate = subCategoryRepository.findSubCategoryByName(subCategoryDTO.getName().trim());
        if(subCate != null) {
            responseVo.setMessage("Danh mục con đã tồn tại !!");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        Category c = categoryRepository.findCategoryByName(subCategoryDTO.getName().trim());
        if(c != null) {
            responseVo.setMessage("Danh mục con không được trùng với cha");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        SubCategory subCategory = convertToEntities.convertSubCategory(subCategoryDTO);
        Category category = categoryRepository.findCategoryById(subCategoryDTO.getCategoryId());
        subCategory.setCategory(category);
        subCategory.setDeletedAt(false);
        subCategoryRepository.save(subCategory);
        responseVo.setMessage("Thêm danh mục con thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateSubCategory(SubCategoryDTO subCategoryDTO) {
        ResponseVo responseVo = new ResponseVo();
        Category c = categoryRepository.findCategoryByName(subCategoryDTO.getName().trim());
        if(c != null) {
            responseVo.setMessage("Danh mục con không được trùng với cha");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }

        SubCategory s = subCategoryRepository.findSubCategoryByIdAndName(subCategoryDTO.getId(),
                subCategoryDTO.getName().trim());
        if(s != null) {
            responseVo.setMessage("Tên danh mục con đã bị trùng !!");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        SubCategory subCategory = convertToEntities.convertSubCategory(subCategoryDTO);
        Category category = categoryRepository.findCategoryById(subCategoryDTO.getCategoryId());
        subCategory.setCategory(category);
        subCategory.setDeletedAt(false);
        subCategoryRepository.save(subCategory);
        responseVo.setMessage("Cập nhập danh mục con thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);

    }
}
