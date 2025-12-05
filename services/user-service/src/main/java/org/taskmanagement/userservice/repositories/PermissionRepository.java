package org.taskmanagement.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taskmanagement.userservice.entities.Permissions;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions,Long> {

}
