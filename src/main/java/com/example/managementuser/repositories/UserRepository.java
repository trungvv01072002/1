package com.example.managementuser.repositories;

import com.example.managementuser.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM users u WHERE (:name is null or u.name like %:name%) and (:email is null or u.email like %:email%)")
    Page<User> findAllUserByFilter(@Param("name") String name, @Param("email") String email, Pageable pageable);
}
