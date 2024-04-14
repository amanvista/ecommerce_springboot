package com.techstackgo.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String mobile;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();
    @Embedded
    @ElementCollection
    @CollectionTable(name="payment_information",joinColumns = @JoinColumn(name = "user_id"))
    private List<PaymentInformation> paymentInformation = new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    private LocalDateTime createdAt;
    List<GrantedAuthority> authorities;
    public User(String email,String password, List<GrantedAuthority> authorities){
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
}
