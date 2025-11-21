package com.ecommerce.profileservice.service;

import com.ecommerce.profileservice.dto.ProfileDto;
import com.ecommerce.profileservice.model.UserProfile;
import com.ecommerce.profileservice.repository.ProfileRepository;
import com.ecommerce.profileservice.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    private Long currentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (UserPrincipal) auth.getPrincipal();
        return principal.userId();
    }

    @Transactional
    public ProfileDto getMyProfile() {
        Long userId = currentUserId();
        return profileRepository.findById(userId)
                .map(p -> new ProfileDto(p.getId(), p.getFullName(), p.getEmail(), p.getPhotoUrl()))
                .orElseGet(() -> new ProfileDto(userId, null, null, null));
    }

    @Transactional
    public ProfileDto updateMyProfile(ProfileDto profileDto) {
        Long userId = currentUserId();
        var profile = profileRepository.findById(userId).orElse(UserProfile.builder().id(userId).build());
        profile.setEmail(profileDto.email());
        profile.setFullName(profileDto.email());
        profile.setPhotoUrl(profileDto.photoUrl());
        profileRepository.save(profile);
        return new ProfileDto(profileDto.userId(), profileDto.fullName(), profile.getEmail(), profile.getPhotoUrl());
    }
}
