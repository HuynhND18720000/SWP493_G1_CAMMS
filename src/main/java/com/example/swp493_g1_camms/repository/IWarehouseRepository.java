package com.example.swp493_g1_camms.repository;


import com.example.swp493_g1_camms.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
@Repository
@EnableJpaRepositories
public interface IWarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query("SELECT w FROM Warehouse w WHERE w.id = ?1")
    public Warehouse findWarehouseById(Long id);

    Page<Warehouse> findAllWarehouseByDeletedAt(boolean deletedAt, Pageable pageable);

    @Query("SELECT w FROM Warehouse w WHERE w.name = ?1")
    Warehouse findWarehouseByName(String warehouseName);
}
