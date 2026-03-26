package com.kira.shopping_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private String status;
    private String message;
    private List<OrderItemDTO> items;
}
