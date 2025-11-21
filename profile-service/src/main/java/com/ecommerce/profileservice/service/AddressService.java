package com.ecommerce.profileservice.service;


import com.ecommerce.profileservice.config.SecurityConfig;
import com.ecommerce.profileservice.model.UserAddress;
import com.ecommerce.profileservice.repository.AddressRepository;
import com.ecommerce.profileservice.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private Long currentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserPrincipal) auth.getPrincipal();
        return principal.userId();
    }

    public List<UserAddress> list() {
        return addressRepository.findAllByUserId(currentUserId());
    }

    @Transactional
    public UserAddress create(UserAddress address) {
        address.setUserId(currentUserId());
        return addressRepository.save(address);
    }

    @Transactional
    public UserAddress update(Long id, UserAddress update) {
        var userId = currentUserId();
        var existing = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!existing.getUserId().equals(userId)) throw new IllegalStateException("Not owner");
        existing.setCity(update.getCity());
        existing.setCountry(update.getCountry());
        existing.setStreet(update.getStreet());
        existing.setZipCode(update.getZipCode());
        existing.setPrimaryAddress(update.isPrimaryAddress());
        return addressRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        var userId = currentUserId();
        var existing = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!existing.getUserId().equals(userId)) throw new IllegalStateException("Not owner");
        addressRepository.delete(existing);
    }


}
