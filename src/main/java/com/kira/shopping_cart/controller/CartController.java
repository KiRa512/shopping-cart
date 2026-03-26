package com.kira.shopping_cart.controller;

import com.kira.shopping_cart.dto.CartDTO;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.mapper.CartMapper;
import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.response.APIResponse;
import com.kira.shopping_cart.service.cart.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final ICartService cartService;
    private final CartMapper cartMapper;
    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<APIResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            CartDTO cartDTO = cartMapper.toCartDTO(cart);

            return ResponseEntity.ok(new APIResponse("Success", cartDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<APIResponse> clearCart( @PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new APIResponse("Clear Cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<APIResponse> getTotalAmount( @PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new APIResponse("Total Price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
