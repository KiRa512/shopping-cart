package com.kira.shopping_cart.repo;

import com.kira.shopping_cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
