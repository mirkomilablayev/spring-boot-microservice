package com.ecommerce.profileservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_addresses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private boolean primaryAddress;

}
