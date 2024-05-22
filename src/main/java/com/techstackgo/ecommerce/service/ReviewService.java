package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.ReviewRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Review;
import com.techstackgo.ecommerce.model.User;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
