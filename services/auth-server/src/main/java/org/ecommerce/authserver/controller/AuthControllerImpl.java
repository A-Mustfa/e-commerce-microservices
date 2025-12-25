package org.ecommerce.authserver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.ecommerce.authserver.dto.UserRegisterRequest;
import org.ecommerce.authserver.dto.UserRegisterResponse;
import org.ecommerce.authserver.configuration.security.JwtService;
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
public class AuthControllerImpl implements AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        UserRegisterResponse response = userService.register(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<String> token(Authentication authentication) {
        String token = jwtService.generate(authentication);
        return ResponseEntity.ok().body(token);
    }

    @Override
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRegisterResponse>> showAllUsers(){
        List<UserRegisterResponse> response = userService.getAllUsers();
        return ResponseEntity.ok().body(response);
    }

    @Override
    @PatchMapping("/postpone/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity suspendUserById(@PathVariable Long userId){
        userService.suspend(userId);
        return ResponseEntity.noContent().build();
    }

}
