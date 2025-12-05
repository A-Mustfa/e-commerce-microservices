package org.taskmanagement.userservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.taskmanagement.userservice.dto.RegistrationRequest;
import org.taskmanagement.userservice.entities.Role;
import org.taskmanagement.userservice.entities.User;
import org.taskmanagement.userservice.exceptions.EmailAlreadyExists;
import org.taskmanagement.userservice.exceptions.EmailNotFoundException;
import org.taskmanagement.userservice.repositories.RoleRepository;
import org.taskmanagement.userservice.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;



    public User register(RegistrationRequest registrationRequest) {
        Role registerationRole = roleService.getRole(registrationRequest.getRole());
        User registeredUser = createUser(registrationRequest, registerationRole);
        User savedUser = userRepository.save(registeredUser);
        return savedUser;
    }

    public void removeUser(String email) {
        if(isEmailExists(email)) {
            userRepository.removeByEmail(email);
        }
        else{
            throw new EmailNotFoundException("Email not found");
        }
    }


    private boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    private User createUser(RegistrationRequest registrationRequest, Role role) {
        if(isEmailExists(registrationRequest.getEmail())) {
            throw new EmailAlreadyExists("this email registered before");
        }
        User newUser =
                User.builder()
                        .email(registrationRequest.getEmail())
                        .password(passwordEncoder.encode(registrationRequest.getPassword()))
                        .role(role)
                        .build();
        return newUser;
    }

}
