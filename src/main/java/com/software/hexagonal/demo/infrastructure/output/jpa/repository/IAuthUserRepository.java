package com.software.hexagonal.demo.infrastructure.output.jpa.repository;

import com.software.hexagonal.demo.infrastructure.output.jpa.entity.AuthUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthUserRepository extends JpaRepository<AuthUserEntity, Long> {
    Optional<AuthUserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
