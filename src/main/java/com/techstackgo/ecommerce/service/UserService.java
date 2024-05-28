package com.techstackgo.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.techstackgo.ecommerce.dto.UserDto;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User getByUsername(String username);

    public User findUserProfileByJwt(String jwt) throws UserException;

    public UserDto getDtoByUsername(String username);

    // public User loadUserByUsername(String username);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    // public User saveUser(User user);
}
