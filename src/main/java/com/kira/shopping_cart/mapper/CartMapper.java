package com.kira.shopping_cart.mapper;

import com.kira.shopping_cart.dto.CartDTO;
import com.kira.shopping_cart.dto.CartItemDTO;
import com.kira.shopping_cart.model.Cart;
import com.kira.shopping_cart.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {

    @Mapping(source = "id", target = "cartId")
    CartDTO toCartDTO(Cart cart);

    @Mapping(source = "id", target = "itemId")
    CartItemDTO map(CartItem item);
}
