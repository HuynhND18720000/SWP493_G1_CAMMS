package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface IProductRepository extends JpaRepository<Product, Long> {

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

    @Query("SELECT p FROM Product p Where p.name = ?1 AND p.deletedAt = false" )
    Product findProductByName(String productName);

    @Query("SELECT p FROM Product p Where p.productCode= ?1 AND p.deletedAt = false" )
    Product findProductByProductCode(String productCode);

    @Query("SELECT p FROM Product p  Where p.id=?1 and p.name = ?2 AND p.deletedAt = false")
    Product findProductByIdAndName(Long id,String name);

    @Query("SELECT p FROM Product p  Where p.id=?1 and  p.productCode = ?2 AND p.deletedAt = false")
    Product findProductByIdAndProductCode(Long id, String productCode);

    @Query("SELECT p FROM Product as p, Manufacturer as m WHERE m.id = p.manufacturer.id AND m.id = ?1")
    List<Product> getAllProductByManufacturerId(Long manufacturerId);

    @Query("SELECT p FROM Product p WHERE p.id IN (?1) AND p.deletedAt = false")
    List<Product> findListAllByConsignmentList(Set<Long> id);

    @Query("select p from Consignment c join ConsignmentProduct cp on c.id = cp.consignment.id" +
            " join Product p on p.id = cp.product.id where c.id = ?1")
    Product findProductByConsignmentId(Long consignment_Id);

    @Query(value = "select distinct p.* from order_detail as od\n" +
            "join camms.order as o on o.id = od.order_id\n" +
            "join consignment as c on c.id = od.consignment_id\n" +
            "join consignment_product as cp on cp.consignment_id = c.id\n" +
            "join product as p on p.id = cp.product_id\n" +
            "where o.status_id = 2 and o.deleted_at = false and o.order_type_id = 1 and cp.quantity_sale > 0",
            nativeQuery = true)
    List<Product> getListProductInWarehouse();

    @Query(nativeQuery = true, value = "select * from Product where deleted_at = false")
    List<Product> getAllProduct();
}
