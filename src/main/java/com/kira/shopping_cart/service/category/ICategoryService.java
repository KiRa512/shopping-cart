package com.kira.shopping_cart.service.category;

import com.kira.shopping_cart.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(String categoryName, Long id);
    void deleteCategoryById(Long id);

}