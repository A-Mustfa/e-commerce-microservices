package org.ecommerce.authserver.services;
import commons.utils.Exceptions.ResourceNotFoundException;
import commons.utils.Exceptions.UserAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.ecommerce.authserver.dto.*;
import org.ecommerce.authserver.entities.User;
import org.ecommerce.authserver.mapper.UserMapper;
import org.ecommerce.authserver.proxies.CustomerServiceClient;
import org.ecommerce.authserver.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerServiceClient customerServiceClient;
    private final UserMapper mapper;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        if(isUserExist(userRegisterRequest.email())){
            throw new UserAlreadyExists("this email registered already: " + userRegisterRequest.email() );
        }
        User newUser  = User.builder()
                .email(userRegisterRequest.email())
                .password(passwordEncoder.encode(userRegisterRequest.password()))
                .role(userRegisterRequest.role())
                .build();
        User savedUser = userRepository.save(newUser);
        customerServiceClient.createCustomer(savedUser.getId());
        return mapper.toRegisterResponse(savedUser);
    }

    public List<UserRegisterResponse> getAllUsers() {
        List<UserProjection> users = userRepository.findAllBy();
        List<UserRegisterResponse> userRegisterRespons = users.stream()
                .map(user -> new UserRegisterResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();
        return userRegisterRespons;
    }

    public void suspend(Long userId) {
        User user = getUserById(userId);
        user.setStatus(User.Status.SUSPENDED);
        userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("no user with id: " + userId)
        );
    }

    public boolean isUserExist(String email) {
        return userRepository.existsByEmail(email);
    }
}

