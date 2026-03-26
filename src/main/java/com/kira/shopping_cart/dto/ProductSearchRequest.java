package com.kira.shopping_cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductSearchRequest {
    private String brand;
    private String name;
    private String category;
}