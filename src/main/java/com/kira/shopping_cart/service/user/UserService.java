package com.kira.shopping_cart.service.user;

import com.kira.shopping_cart.dto.UserDTO;
import com.kira.shopping_cart.exceptions.AlreadyExistsException;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.mapper.UserMapper;
import com.kira.shopping_cart.model.User;
import com.kira.shopping_cart.repo.UserRepo;
import com.kira.shopping_cart.request.CreateUserRequest;
import com.kira.shopping_cart.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return userMapper.userDTO(user);
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        if(userRepo.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        User saved = userRepo.save(user);
        return userMapper.userDTO(saved);
    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequest request) {
        User updated = userRepo.findById(userId).map(user -> {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return userRepo.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.userDTO(updated);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete, () -> {
            throw new ResourceNotFoundException("User not found");
        });
    }
}
