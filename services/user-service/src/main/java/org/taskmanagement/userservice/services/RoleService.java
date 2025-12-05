package org.taskmanagement.userservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taskmanagement.userservice.entities.Role;
import org.taskmanagement.userservice.exceptions.RoleNotFound;
import org.taskmanagement.userservice.repositories.RoleRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(String roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFound("no such error named: " + roleName));
        return role;
    }
}
