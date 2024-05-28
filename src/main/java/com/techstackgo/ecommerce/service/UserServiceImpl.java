package com.techstackgo.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techstackgo.ecommerce.dto.UserDto;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.mapper.UserMapper;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private UserMapper mapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found With Email" + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        var user = userRepository.findByUsernameAndIsEnabledTrue(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user not found!");
        }
        return user.get();
    }

    public User findUserById(Long userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("userId not found!");
        }
        return user.get();
    }

    public UserDto getDtoByUsername(String username) {
        return mapper.toDto(getByUsername(username));
    }

    public User findUserProfileByJwt(String jwt) throws UserException {
        return new User();
    }

}