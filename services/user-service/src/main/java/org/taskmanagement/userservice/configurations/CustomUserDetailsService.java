package org.taskmanagement.userservice.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.taskmanagement.userservice.entities.User;
import org.taskmanagement.userservice.repositories.UserRepository;
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithRoles(email).orElseThrow(
                () -> {throw new UsernameNotFoundException(email);
                }
        );

        return new CustomUserDetails(user);
    }
}
