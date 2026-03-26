package com.kira.shopping_cart.service.product;

import com.kira.shopping_cart.dto.ProductDTO;
import com.kira.shopping_cart.dto.ProductSearchRequest;
import com.kira.shopping_cart.model.Product;
import com.kira.shopping_cart.request.AddProductRequest;
import com.kira.shopping_cart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    ProductDTO addProduct(AddProductRequest product);
    ProductDTO getProductById(Long id);
    Product getProductByIdForCart(Long id);
    Product getProductEntityById(Long id);
    void deleteProductById(Long id);
    ProductDTO updateProduct(ProductUpdateRequest product, Long productId);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsByBrand(String brand);
    List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDTO> getProductsByName(String name);
    List<ProductDTO> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDTO> searchProducts(ProductSearchRequest productSearchRequest);

//    List<ProductDTO> getConvertedProducts(List<Product> products);
//
//    ProductDto convertToDto(Product product);
}