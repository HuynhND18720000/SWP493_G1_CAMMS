package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {


    @Query(value = "SELECT * FROM category WHERE MATCH(name) "
            + "AGAINST (?1) AND deleted_at = false", nativeQuery = true )
    Page<Category> findBySearch(String name, Pageable pageable);

    Page<Category> findAllByDeletedAt(boolean deletedAt, Pageable pageable);

    @Query("SELECT c FROM Category c Where c.id = ?1" )
    Category findCategoryById(Long categoryId);

    @Query("SELECT c FROM Category c Where c.name = ?1" )
    Category findCategoryByName(String categoryName);

    @Query("SELECT c FROM Category c  Where c.id <> ?1 and c.name = ?2 AND c.deletedAt = false")
    Category findCategoryByIdAndName(Long id, String categoryName);

    @Query("SELECT c FROM Category c")
    List<Category> getAllCategory();

}

