package com.ecommerce.profileservice.repository;

import com.ecommerce.profileservice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile, Long> {

}
