package com.techstackgo.ecommerce.service;
import com.techstackgo.ecommerce.dto.CreateProductRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProductService {
    public Product createProduct(CreateProductRequest req);
    public String deleteProduct(Long productId)throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    public Product findProductById(Long id) throws ProductException;
//    public List<Product> findProductByCategory(String category);
    public Page<Product> getAllProduct(List<String> colors, List<String> sizes, Integer minPrice,
                              Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
}
