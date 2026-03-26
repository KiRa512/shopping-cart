package com.kira.shopping_cart.service.cart;

import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void updateCartItem(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    Cart getCartItems(Long cartId);

    CartItem getCartItem(Long cartId, Long productId);
}
