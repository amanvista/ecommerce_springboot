package com.techstackgo.ecommerce.controller;

import com.techstackgo.ecommerce.config.JwtProvider;
import com.techstackgo.ecommerce.dto.AuthRequest;
import com.techstackgo.ecommerce.dto.AuthResponse;
import com.techstackgo.ecommerce.dto.LoginRequest;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.UserRepository;
import com.techstackgo.ecommerce.service.UserService;
import com.techstackgo.ecommerce.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // @Autowired
    // AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userServiceImpl;

    @Autowired
    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder,
            UserService userServiceImpl) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String passsword = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User emailExist = userRepository.findUserByEmail(email);
        if (emailExist != null) {
            throw new UserException("Email is Already Used With Another Account");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(passsword));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        // createdUser.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(createdUser);
        // Authentication authentication = new
        // UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String token = jwtProvider.generateToken(email);

        AuthResponse authResponse = new AuthResponse("Signup Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@Valid @RequestBody LoginRequest loginRequest)
            throws UserException {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // Authentication authentication = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(username, password));
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(username);
        AuthResponse authResponse = new AuthResponse("Signin Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public String authenticateHandler(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        // Authenticate the user
        Authentication authentication = authenticate(username, password);

        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authRequest.getUsername());
    }

}
