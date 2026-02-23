package com.worknest.repository;

import com.worknest.entity.Role;
import com.worknest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ================= AUTH =================
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // ================= ROLE BASED =================
    long countByRole(Role role);

    List<User> findByRole(Role role);

    // ================= MANAGER =================
    List<User> findByManager(User manager);
}
