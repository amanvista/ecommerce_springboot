package com.techstackgo.ecommerce.controller;

import com.techstackgo.ecommerce.config.JwtProvider;
import com.techstackgo.ecommerce.dto.AuthRequest;
import com.techstackgo.ecommerce.dto.AuthResponse;
import com.techstackgo.ecommerce.dto.LoginRequest;
import com.techstackgo.ecommerce.dto.RoleDto;
import com.techstackgo.ecommerce.dto.SignupSuccess;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Role;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.UserRepository;
import com.techstackgo.ecommerce.service.RoleService;
import com.techstackgo.ecommerce.service.TokenService;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // @Autowired
    // AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private UserService userServiceImpl;
    private TokenService tokenService;
    private RoleService roleService;

    @Autowired
    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder,
            UserService userServiceImpl, TokenService tokenService, RoleService roleService) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
        this.tokenService = tokenService;
        this.roleService = roleService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupSuccess> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User emailExist = userRepository.findUserByEmail(email);
        if (emailExist != null) {
            throw new UserException("Email is Already Used With Another Account");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setUsername(email);
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setEnabled(true);
        Long id = (long) 1;
        Role userRole = roleService.getById(id).get();
        createdUser.setRoles(Set.of(userRole));
        // createdUser.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(createdUser);
        // Authentication authentication = new
        // UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String token = jwtProvider.generateToken(email);

        SignupSuccess authResponse = new SignupSuccess("Signup Success");
        return new ResponseEntity<SignupSuccess>(authResponse, HttpStatus.CREATED);
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
            throws UserException, Exception {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // Authentication authentication = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(username, password));
        Authentication authentication = authenticate(username, password);
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateAccessToken(username);
        AuthResponse authResponse = new AuthResponse("Signin Success", token);
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
