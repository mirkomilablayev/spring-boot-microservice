package com.ecommerce.profileservice.controller;

import com.ecommerce.profileservice.dto.ProfileDto;
import com.ecommerce.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ProfileDto> me() {
        return ResponseEntity.ok(profileService.getMyProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileDto> update(@Valid @RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.updateMyProfile(dto));
    }

}
