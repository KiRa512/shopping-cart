package com.kira.shopping_cart.service.product;

import com.kira.shopping_cart.dto.ProductDTO;
import com.kira.shopping_cart.dto.ProductSearchRequest;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import com.kira.shopping_cart.mapper.ProductMapper;
import com.kira.shopping_cart.model.Category;
import com.kira.shopping_cart.model.Product;
import com.kira.shopping_cart.repo.CategoryRepo;
import com.kira.shopping_cart.repo.ProductRepo;
import com.kira.shopping_cart.request.AddProductRequest;
import com.kira.shopping_cart.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO addProduct(AddProductRequest product) {
        Category category = categoryRepo.findById(product.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + product.getCategoryId()));
        Product newProduct = productMapper.toEntity(product);
        newProduct.setCategory(category);
        Product saved = productRepo.save(newProduct);
        return productMapper.toDTO(saved);

    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    @Override
    public Product getProductByIdForCart(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product getProductEntityById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteProductById(Long id) {
             productRepo.findById(id).ifPresentOrElse(
                     productRepo::delete,
                () -> { throw new ResourceNotFoundException("Product not found with id: " + id); });
    }



    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return productMapper.toDTO(products);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepo.findByCategoryName(category);
        return productMapper.toDTO(products);
    }

    @Override
    public List<ProductDTO> getProductsByBrand(String brand) {
        List<Product> products = productRepo.findByBrand(brand);
        return productMapper.toDTO(products);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepo.findByCategoryNameAndBrand(category, brand);
        return productMapper.toDTO(products);
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        List<Product> products = productRepo.findByName(name);
        return productMapper.toDTO(products);
    }

    @Override
    public List<ProductDTO> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepo.findByBrandAndName(brand, name);
        return productMapper.toDTO(products);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return (long) productRepo.findByBrandAndName(brand, name).size();
    }

    @Override
    public List<ProductDTO> searchProducts(ProductSearchRequest productSearchRequest) {
        List<Product> products = productRepo.searchProducts(
                productSearchRequest.getBrand(),
                productSearchRequest.getName(),
                productSearchRequest.getCategory()
        );
        return productMapper.toDTO(products);
    }


    @Override
    public ProductDTO updateProduct(ProductUpdateRequest product, Long productId) {
        Category category = categoryRepo.findById(product.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + product.getCategoryId()));
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        productMapper.updateProductFromDto(product , existingProduct);
        existingProduct.setCategory(category);
        Product saved = productRepo.save(existingProduct);
        return productMapper.toDTO(saved);
    }
}
