package com.manfredsolutions.boilerplate.domain.port;

import com.manfredsolutions.boilerplate.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    User save(User user);
}
