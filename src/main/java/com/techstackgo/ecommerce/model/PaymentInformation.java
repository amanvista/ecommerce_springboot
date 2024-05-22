package com.techstackgo.ecommerce.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="cardholder_name")
    private String cardHolderName;
    @Column(name="card_number")
    private String cardNumber;
    @Column(name="expiration_date")
    private String expirationDate;
    @Column(name="cvv")
    private String cvv;

}
