package com.kira.shopping_cart.mapper;

import com.kira.shopping_cart.dto.ProductDTO;
import com.kira.shopping_cart.model.Product;
import com.kira.shopping_cart.request.AddProductRequest;
import com.kira.shopping_cart.request.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(AddProductRequest addProductRequest);

    @Mapping(source = "category.name", target = "categoryName")
    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTO(List<Product> products);
    void updateProductFromDto(ProductUpdateRequest updateRequest, @MappingTarget Product existingProduct);
}
