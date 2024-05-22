package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.config.JwtProvider;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    public UserServiceImplementation(UserRepository userRepository,JwtProvider jwtProvider){
        this.userRepository=userRepository;
        this.jwtProvider=jwtProvider;
    }
    @Override
    public User findUserById(Long userId )throws UserException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found with id:"+userId);
    }
    public User findUserProfileByJwt(String jwt )throws UserException{
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findUserByEmail(email);
        if(user==null){
            throw new UserException("User not found with email"+email);
        }
        return user;
    }
}
