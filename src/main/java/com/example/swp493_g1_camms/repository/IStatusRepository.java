package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IStatusRepository extends JpaRepository<Status, Long> {
    @Query(value = "SELECT st FROM Status AS st WHERE st.id=?1")
    Status findStatusById(Long id);
}
