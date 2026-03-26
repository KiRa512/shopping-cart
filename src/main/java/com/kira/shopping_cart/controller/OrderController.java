package com.kira.shopping_cart.controller;

import com.kira.shopping_cart.dto.OrderDTO;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.response.APIResponse;
import com.kira.shopping_cart.service.order.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    @PostMapping("/order")
    public ResponseEntity<APIResponse> createOrder(@RequestParam Long userId) {
        try {
            OrderDTO order =  orderService.placeOrder(userId);
            return ResponseEntity.ok(new APIResponse("Item Order Success!", order));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error Occurred!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Oops!", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDTO> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Oops!", e.getMessage()));
        }
    }
}
