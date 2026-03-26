package com.kira.shopping_cart.service.cart;

import com.kira.shopping_cart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

        Cart getCart(Long id);
        void clearCart(Long id);
        BigDecimal getTotalPrice(Long id);
        Long initializeNewCart();

        Cart getCartByUserId(Long userId);
//        Cart getCartByUserId(Long userId);

}
