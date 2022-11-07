package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product WHERE name LIKE %?1% "
            + "AND deleted_at = false", nativeQuery = true )
    Page<Product> getAllProductByName(String name, Pageable pageable);

    //lay ra list san pham
    Page<Product> findAllProductsBydeletedAt(boolean deleteAt, Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, product_code) "
            + "AGAINST (?1) "
            + "AND category_id = CASE WHEN ?2 IS NULL THEN category_id ELSE ?2 END "
            + "AND manufacturer_id = CASE WHEN ?3 IS NULL THEN manufacturer_id ELSE ?3 END "
            + "AND deleted_at = false"
            , nativeQuery = true )
    Page<Product> findBySearch(String productName, Long categoryId, Long manufactorId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = CASE WHEN ?1 IS NULL THEN p.category.id ELSE ?1 END "
            + "AND p.manufacturer.id = CASE WHEN ?2 IS NULL THEN p.manufacturer.id ELSE ?2 END "
            + "AND p.deletedAt = false")
    Page<Product> findAllBySearch(Long categoryId, Long manufactorId, Pageable pageable);
    //tim kiem product theo id
    @Query("SELECT p FROM Product as p WHERE p.id = ?1 and p.deletedAt=false ")
    Product findProductById(Long productId);
    //get count quantity cua product
    @Query(value = "SELECT p.quantity FROM Product as p WHERE p.id = ?1 " +
            "AND p.deletedAt = false")
    Integer countQuantity(Long productId);

    //get price cua product
    @Query(value = "SELECT p.unitprice FROM Product as p WHERE p.id = ?1 " +
            "AND p.deletedAt = false")
    Double totalPrice(Long productId);
}
