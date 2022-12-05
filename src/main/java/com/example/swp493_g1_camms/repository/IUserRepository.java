package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query(value = "SELECT u FROM User AS u WHERE u.id = ?1")
    Optional<User> getUserById(Long id);
    Optional<User> findByEmail(String email);

    @Query("\n" +
            "select u from User u where u.email = " +
            "(select u.email from User u where u.email = ?1)")
    User getUserByEmail(String email);

    @Query("\n" +
            "select u from User u where u.password = ?1")
    User getUserByPassword(String password);

}
