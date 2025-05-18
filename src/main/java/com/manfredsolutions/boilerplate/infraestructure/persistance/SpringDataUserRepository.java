package com.manfredsolutions.boilerplate.infraestructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<JpaUserEntity, Long> {
    Optional<JpaUserEntity> findByUsername(String username);
    Optional<JpaUserEntity> findById(Long id);
}
