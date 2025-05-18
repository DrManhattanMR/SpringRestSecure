package com.manfredsolutions.boilerplate.infraestructure.persistance;

import com.manfredsolutions.boilerplate.domain.model.User;
import com.manfredsolutions.boilerplate.domain.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SpringDataUserRepository jpa;
    public UserRepositoryImpl(SpringDataUserRepository jpa) {
        this.jpa = jpa;
    }
    @Override
    public Optional<User>findByUsername(String username) {
        return jpa.findByUsername(username).map(this::toDomain);
    }
    @Override
    public Optional<User>findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }
    @Override
    public User save(User user) {
        return toDomain(jpa.save(toEntity(user)));
    }
    private User toDomain(JpaUserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRoles() != null ? entity.getRoles() : Set.of()
        );
    }

    private JpaUserEntity toEntity(User user) {
        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(user.getId()); // null si es nuevo
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setRoles(user.getRoles() != null ? user.getRoles() : Set.of());
        return entity;
    }
}
