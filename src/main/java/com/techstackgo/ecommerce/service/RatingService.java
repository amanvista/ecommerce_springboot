package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.dto.RatingRequest;
import com.techstackgo.ecommerce.exception.ProductException;
import com.techstackgo.ecommerce.model.Rating;
import com.techstackgo.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user)throws ProductException;
    public List<Rating> getProductRating(Long productId);
}
