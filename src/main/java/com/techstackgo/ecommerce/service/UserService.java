package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt)throws UserException;
//    public User saveUser(User user);
}
