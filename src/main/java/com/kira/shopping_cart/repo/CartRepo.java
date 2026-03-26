package com.kira.shopping_cart.repo;

import com.kira.shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {
//    void deleteAllById(Long cartId);
    Cart getCartByUserId(Long userId);


    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.id = :id")
    Optional<Cart> findByIdWithItems(@Param("id") Long id);
}
