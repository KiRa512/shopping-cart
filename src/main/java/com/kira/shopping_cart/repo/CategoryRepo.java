package com.kira.shopping_cart.repo;

import com.kira.shopping_cart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category , Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
