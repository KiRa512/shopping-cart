package com.kira.shopping_cart.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}
