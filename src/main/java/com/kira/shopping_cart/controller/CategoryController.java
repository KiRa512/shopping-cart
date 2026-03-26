package com.kira.shopping_cart.controller;

import com.kira.shopping_cart.exceptions.AlreadyExistsException;
import com.kira.shopping_cart.model.Category;
import com.kira.shopping_cart.service.category.ICategoryService;
import lombok.AllArgsConstructor;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.response.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping()
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return  ResponseEntity.ok(new APIResponse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category name) {
        try {
            Category theCategory = categoryService.addCategory(name);
            return  ResponseEntity.ok(new APIResponse("Success", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id){
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return  ResponseEntity.ok(new APIResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name){
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new APIResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return  ResponseEntity.ok(new APIResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable Long id, @RequestBody String category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new APIResponse("Update success!", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

}