package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.RatingRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.model.Rating;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService{
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductService productService;
    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
