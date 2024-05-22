package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.CreateProductRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Category;
import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.repository.CategoryRepository;
import com.techstackgo.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
//        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
//        if(topLevel==null){
//            Category topLevelCategory = new Category();
//            topLevelCategory.setName(req.getTopLevelCategory());
//            topLevelCategory.setLevel(1);
//            topLevel = categoryRepository.save(topLevelCategory);
//        }
//
//        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
//        if(secondLevel==null){
//            Category secondLevelCategory = new Category();
//            secondLevelCategory.setName(req.getSecondLevelCategory());
//            secondLevelCategory.setLevel(2);
//            secondLevel = categoryRepository.save(secondLevelCategory);
//        }
//        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
//        if(thirdLevel==null){
//            Category thirdLevelCategory = new Category();
//            thirdLevelCategory.setName(req.getThirdLevelCategory());
//            thirdLevelCategory.setLevel(3);
//            thirdLevel = categoryRepository.save(thirdLevelCategory);
//        }

        // Create a new Product entity based on the request
        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
//        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());
        // Save the new product in the database
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        // Check if the product exists
        Product product = findProductById(productId);
        product.getSizes().clear();
        if (product == null) {
            throw new ProductException("Product not found with id: " + productId);
        }

        // Delete the product from the database
        productRepository.delete(product);

        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        // Check if the product exists
        Product existingProduct = findProductById(productId);
        if(req.getQuantity()!=0){
            existingProduct.setQuantity(req.getQuantity());
        }
        if(req.getTitle()!=""){
            existingProduct.setTitle(req.getTitle());
        }
        // Save the updated product in the database
        return productRepository.save(existingProduct);
    }

    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        // Retrieve product by ID from the database
        throw new ProductException("Product not found with id: " + id);
    }

//    @Override
//    public List<Product> findProductByCategory(String category) {
//        // Retrieve products by category from the database
//        return productRepository.findByCategoryName(category);
//    }

    @Override
    public Page<Product> getAllProduct(List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock,
                                       Integer pageNumber, Integer pageSize) {
        // Create a pageable request for pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Fetch products based on category, price, discount, and sorting
        List<Product> products = productRepository.filterProducts(minPrice, maxPrice, minDiscount, sort);

        // Apply color filter if colors are provided and not empty
        if (colors != null && !colors.isEmpty()) {
            products = products.stream()
                    .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        // Apply stock availability filter if stock is provided
        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        // Pagination logic to return a page of filtered products
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, pageable, products.size());
    }
}

