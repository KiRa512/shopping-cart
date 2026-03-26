package com.kira.shopping_cart.service.cart;

import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.model.CartItem;
import com.kira.shopping_cart.model.Product;
import com.kira.shopping_cart.repo.CartItemRepo;
import com.kira.shopping_cart.repo.CartRepo;
import com.kira.shopping_cart.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;
    private final ICartService cartService;
    private final IProductService productService;

    private void updateCartTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);
        cartRepo.save(cart);
    }

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // first check the cart exists
        Cart cart = cartService.getCart(cartId);
        // then check the product exists
        Product product = productService.getProductByIdForCart(productId);
        // then create a new CartItem and save it to the database
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item-> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

            if(cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(product.getPrice());
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
            cartItem.setTotalPrice();
            cartItemRepo.save(cartItem);

            updateCartTotal(cart);
    }

    @Override
    public void updateCartItem(Long cartId, Long productId, int quantity) {
        // Implementation to update item in cart
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId , productId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cartItemRepo.save(cartItem);
        updateCartTotal(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        // Implementation to remove item from cart
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(productId , cartId);
        cart.getItems().remove(cartItem);
        cartItemRepo.delete(cartItem);

    }

    @Override
    public Cart getCartItems(Long cartId) {
        // Implementation to get items in cart
        return cartService.getCart(cartId);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
    }
}
