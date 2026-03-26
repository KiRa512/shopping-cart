package com.kira.shopping_cart.mapper;

import com.kira.shopping_cart.dto.UserDTO;
import com.kira.shopping_cart.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userDTO(User user);
}
