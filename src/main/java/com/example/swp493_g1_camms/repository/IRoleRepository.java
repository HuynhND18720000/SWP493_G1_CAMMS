package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.ERole;
import com.example.swp493_g1_camms.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByname(ERole name);
}
