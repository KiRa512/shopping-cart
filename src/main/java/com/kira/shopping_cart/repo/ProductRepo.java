package com.kira.shopping_cart.repo;

import com.kira.shopping_cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    List<Product> findByCategoryName(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product> findByBrandAndName(String brand, String name);

    @Query("SELECT p FROM Product p " +
            "WHERE (:brand IS NULL OR LOWER(p.brand) = LOWER(:brand)) " +
            "AND (:name IS NULL OR LOWER(p.name) = LOWER(:name)) " +
            "AND (:category IS NULL OR LOWER(p.category.name) = LOWER(:category))")
    List<Product> searchProducts(@Param("brand") String brand,
                                 @Param("name") String name,
                                 @Param("category") String category);
}
