package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IStaffRepository extends JpaRepository<Staff, Long> {

    Page<Staff> findAllByDeletedAt(boolean deletedAt, Pageable pageable);

    @Query("SELECT s FROM Staff s " + "Where s.id = ?1" )
    Staff getDetailStaff(Long id);

    @Query(value = "SELECT * FROM staff WHERE MATCH(full_name) "
            + "AGAINST (?1) AND deleted_at = false", nativeQuery = true )
    Page<Staff> findBySearch(String fullName, Pageable pageable);


}
