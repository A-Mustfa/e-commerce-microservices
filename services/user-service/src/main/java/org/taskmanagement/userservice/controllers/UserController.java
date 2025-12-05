package org.taskmanagement.userservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskmanagement.userservice.dto.RegisterationResponse;
import org.taskmanagement.userservice.dto.RegistrationRequest;
import org.taskmanagement.userservice.entities.User;
import org.taskmanagement.userservice.mappers.Mapper;
import org.taskmanagement.userservice.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final Mapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterationResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User registeredUser = userService.register(registrationRequest);
        RegisterationResponse response = mapper.toResponse(registeredUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove")
    public ResponseEntity removeUser(@RequestBody Map<String,String> email) {
        userService.removeUser(email.get("email"));
        return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
    }
}
