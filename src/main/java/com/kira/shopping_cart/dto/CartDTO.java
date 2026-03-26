package com.kira.shopping_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private Set<CartItemDTO> items;
    private BigDecimal totalAmount;
}