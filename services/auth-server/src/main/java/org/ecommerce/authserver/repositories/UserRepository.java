package org.ecommerce.authserver.repositories;

import org.ecommerce.authserver.dto.UserProjection;
import org.ecommerce.authserver.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<UserProjection> findAllBy();
    User save(User user);

    boolean existsByEmail(String email);
}
