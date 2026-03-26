package com.kira.shopping_cart.controller;

import com.kira.shopping_cart.dto.UserDTO;
import com.kira.shopping_cart.exceptions.AlreadyExistsException;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.model.User;
import com.kira.shopping_cart.request.CreateUserRequest;
import com.kira.shopping_cart.request.UpdateUserRequest;
import com.kira.shopping_cart.response.APIResponse;
import com.kira.shopping_cart.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<APIResponse> getUserById(@PathVariable Long userId) {
        try {
            UserDTO userDto = userService.getUserById(userId);
            return ResponseEntity.ok(new APIResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            UserDTO userDto = userService.createUser(request);
            return ResponseEntity.ok(new APIResponse("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<APIResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            UserDTO userDto = userService.updateUser(userId, request);
            return ResponseEntity.ok(new APIResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new APIResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
