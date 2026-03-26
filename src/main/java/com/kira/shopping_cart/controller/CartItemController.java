package com.kira.shopping_cart.controller;

import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.response.APIResponse;
import com.kira.shopping_cart.service.cart.ICartItemService;
import com.kira.shopping_cart.service.cart.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;


    @PostMapping("/item/add")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            if (cartId == null) {
                cartId= cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Add Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<APIResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new APIResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<APIResponse> updateItemQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long itemId,
                                                           @RequestParam Integer quantity) {
        try {
            cartItemService.updateCartItem(cartId, itemId, quantity);
            return ResponseEntity.ok(new APIResponse("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }
}
