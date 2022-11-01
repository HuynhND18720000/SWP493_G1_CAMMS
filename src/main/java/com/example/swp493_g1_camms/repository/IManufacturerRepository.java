package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    @Query(value = "SELECT * FROM manufacturer WHERE name LIKE %?1% AND deleted_at = false", nativeQuery = true )
    Page<Manufacturer> findAllManufacturerByName(String name, Pageable pageable);

    @Query("SELECT m FROM Manufacturer m WHERE m.id = ?1")
    public Manufacturer findManufacturerById(Long id);
    Page<Manufacturer> findAllManufacturerByDeletedAt(boolean deletedAt, Pageable pageable);

}