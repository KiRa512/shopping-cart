package com.kira.shopping_cart.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
