package com.kira.shopping_cart.service.cart;

import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.repo.CartItemRepo;
import com.kira.shopping_cart.repo.CartRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CartService implements ICartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    @Override
    public Cart getCart(Long id) {
        return cartRepo.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cartItemRepo.deleteAllByCartId(id);
        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepo.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepo.findById(id).get();
        return cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    @Override
//    public void addItemToCart(Long cartId, Long productId, int quantity) {
//       Cart cart =  cartRepo.findById(cartId)
//                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
//
//    }

    @Override
    public Long initializeNewCart() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);
        Cart savedCart = cartRepo.save(cart);
        return savedCart.getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.getCartByUserId(userId);
    }


//    @Override
//    public Cart getCartByUserId(Long userId) {
//        return cartRepo.findAll().stream()
//                .filter(cart -> cart.getUser().getId().equals(userId))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId));
//    }
}

