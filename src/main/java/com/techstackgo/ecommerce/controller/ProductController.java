package com.techstackgo.ecommerce.controller;

import com.techstackgo.ecommerce.dto.CreateProductRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Category;
import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    // @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategory(
            // @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        Page<Product> res = productService.getAllProduct(color, size, minPrice, maxPrice, minDiscount, sort, stock,
                pageNumber, pageSize);
        System.out.println("Complete Products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    // Helper method to map ProductRequest to Product entity
    private Product mapToProductEntity(CreateProductRequest productRequest) {
        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setDiscountedPrice(productRequest.getDiscountedPrice());
        product.setDiscountPresent(productRequest.getDiscountPresent());
        product.setQuantity(productRequest.getQuantity());
        product.setBrand(productRequest.getBrand());
        product.setColor(productRequest.getColor());
        // product.setSizes(productRequest.getSizes());
        product.setImageUrl(productRequest.getImageUrl());
        // Set other properties as needed
        Category category = new Category();
        category.setName(productRequest.getSecondLevelCategory());
        category.setLevel(0);
        category.setParentCategory(null);
        // product.setCategory(category);
        return product;
    }

    // Endpoint to add a new product
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        // Map ProductRequest to Product entity
        Product newProduct = mapToProductEntity(createProductRequest);
        // Save the new product using ProductService
        Product savedProduct = productService.createProduct(createProductRequest);
        // Return the saved product in the response body with HTTP status CREATED (201)
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws ProductException {
        // Call the productService to retrieve a product by its ID
        Product product = productService.findProductById(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProductById(@PathVariable Long productId,
            @RequestBody CreateProductRequest createProductRequest) throws ProductException {
        Product existingProduct = productService.findProductById(productId);
        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Product updatedProduct = mapToProductEntity(createProductRequest);
            Product savedProduct = productService.updateProduct(productId, updatedProduct);
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        }
    }
}
