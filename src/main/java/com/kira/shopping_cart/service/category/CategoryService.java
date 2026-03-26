package com.kira.shopping_cart.service.category;

import com.kira.shopping_cart.exceptions.AlreadyExistsException;
import com.kira.shopping_cart.model.Category;
import com.kira.shopping_cart.repo.CategoryRepo;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
//        return  Optional.of(category).filter(c -> !categoryRepo.existsByName(c.getName()))
//                .map(categoryRepo :: save)
//                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists"));

        if (categoryRepo.existsByName(category.getName())) {
            throw new AlreadyExistsException(category.getName() + " already exists!");
        }
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(String categoryName, Long id) {
//        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
//            oldCategory.setName(category.getName());
//            return categoryRepo.save(oldCategory);
//        }) .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));

//        Category existingCategory = categoryRepo.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found"))
//        existingCategory.setName(category.getName());
//        return categoryRepo.save(existingCategory);

        return categoryRepo.findById(id).map(
                existingCategory -> {
                    existingCategory.setName(categoryName);
                    return categoryRepo.save(existingCategory);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

    }


    @Override
    public void deleteCategoryById(Long id) {
        categoryRepo.findById(id)
                .ifPresentOrElse(categoryRepo::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });

    }
}