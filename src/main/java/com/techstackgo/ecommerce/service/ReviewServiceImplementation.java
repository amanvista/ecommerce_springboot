package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.ReviewRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.model.Review;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.ProductRepository;
import com.techstackgo.ecommerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
