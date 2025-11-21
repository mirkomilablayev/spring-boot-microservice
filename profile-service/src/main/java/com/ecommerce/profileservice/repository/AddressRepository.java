package com.ecommerce.profileservice.repository;

import com.ecommerce.profileservice.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAllByUserId(Long userId);
}
