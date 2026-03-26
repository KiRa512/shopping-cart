package com.kira.shopping_cart.service.user;


import com.kira.shopping_cart.dto.UserDTO;
import com.kira.shopping_cart.request.CreateUserRequest;
import com.kira.shopping_cart.request.UpdateUserRequest;

public interface IUserService {
    UserDTO getUserById(Long userId);
    UserDTO createUser(CreateUserRequest request);
    UserDTO updateUser(Long userId, UpdateUserRequest request);
    void deleteUser(Long userId);
}
