package com.kira.shopping_cart.repo;

import com.kira.shopping_cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
