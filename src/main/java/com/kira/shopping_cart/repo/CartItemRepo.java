package com.kira.shopping_cart.repo;

import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
        void deleteAllByCartId(Long cartId);

}
