package org.ecommerce.authserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.ecommerce.authserver.dto.RegisterRequest;
import org.ecommerce.authserver.dto.UserResponse;
import org.ecommerce.authserver.mapper.UserMapper;
import org.ecommerce.authserver.services.JwtService;

import org.ecommerce.authserver.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper mapper;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        UserResponse response = mapper.toRegisterResponse(userService.register(registerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> token(Authentication authentication) {
        String token = jwtService.generate(authentication);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> showAllUsers(){
        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/postpone/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity showUserById(@PathVariable Long userId){
        userService.suspend(userId);
        return ResponseEntity.noContent().build();
    }

}
