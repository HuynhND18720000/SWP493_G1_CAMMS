package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubCategoryRepository extends JpaRepository<SubCategory, Long> {


    @Query("SELECT sc FROM SubCategory sc Where sc.category.id = ?1" )
    List<SubCategory> getSubCategoryByCateId(Long categoryId);

    @Query("SELECT sc FROM SubCategory sc "
            + "Where sc.category.id = ?1 " )
    Page<SubCategory> findAllSubCategory(Long categoryId, Pageable pageable);

    @Query("SELECT sc FROM SubCategory sc Where sc.name = ?1" )
    SubCategory findSubCategoryByName(String subCategoryName);

    @Query("SELECT sc FROM SubCategory sc  Where sc.id <> ?1 and sc.name = ?2")
    SubCategory findSubCategoryByIdAndName(Long id, String categoryName);

    @Query("SELECT sc FROM SubCategory sc Where sc.id = ?1 AND sc.deletedAt = false" )
    SubCategory findSubCategoryById(Long subCategoryId);

}