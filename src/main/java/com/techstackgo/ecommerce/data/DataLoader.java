package com.techstackgo.ecommerce.data;

import com.techstackgo.ecommerce.model.Product;
import com.techstackgo.ecommerce.model.RoleDto;
import com.techstackgo.ecommerce.model.User;
import com.techstackgo.ecommerce.repository.CartItemRepository;
import com.techstackgo.ecommerce.repository.CartRepository;
import com.techstackgo.ecommerce.repository.ProductRepository;
import com.techstackgo.ecommerce.repository.UserRepository;
import com.techstackgo.ecommerce.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {

        // Create authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        // Preload User
        User user = new User("example_user", "aman@gmail.com", authorities);
        user.setFirstName("Aman");
        user.setLastName("Bisht");
        user.setMobile("723932423423");
        user.setRole("ADMIN");
        // user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        RoleDto adminRole = new RoleDto();
        adminRole.setName("ADMIN");
        adminRole = roleService.createRole(adminRole);

        RoleDto userRole = new RoleDto();
        userRole.setName("USER");
        userRole = roleService.createRole(userRole);

        RoleDto modRole = new RoleDto();
        modRole.setName("MODERATOR");
        modRole = roleService.createRole(modRole);
    }
}
