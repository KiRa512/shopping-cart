package com.kira.shopping_cart.controller;

import com.kira.shopping_cart.dto.ProductDTO;
import com.kira.shopping_cart.dto.ProductSearchRequest;
import com.kira.shopping_cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.kira.shopping_cart.request.AddProductRequest;
import com.kira.shopping_cart.request.ProductUpdateRequest;
import com.kira.shopping_cart.response.APIResponse;
import com.kira.shopping_cart.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return  ResponseEntity.ok(new APIResponse("success", products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId) {
        try {
            ProductDTO productDto = productService.getProductById(productId);
            return  ResponseEntity.ok(new APIResponse("success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            ProductDTO productDto = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Add product success!", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}")
    public  ResponseEntity<APIResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            ProductDTO productDto = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new APIResponse("Update product success!", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new APIResponse("Delete product success!", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    /**
     * Unified search endpoint. Use query parameters to filter: ?brand=&name=&category=
     * Behaviour: if multiple params provided, try brand+name first, then category+brand, else single filters.
     */
    @GetMapping("/search")
    public ResponseEntity<APIResponse> searchProducts(@ModelAttribute ProductSearchRequest productSearchRequest) {
        List<ProductDTO> products = productService.searchProducts(productSearchRequest);
        if (products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new APIResponse("No products found", null));
        }

        return ResponseEntity.ok(new APIResponse("success", products));
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new APIResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }


}