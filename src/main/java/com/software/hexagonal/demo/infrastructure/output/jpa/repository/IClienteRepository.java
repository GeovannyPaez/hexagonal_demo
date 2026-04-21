package com.software.hexagonal.demo.infrastructure.output.jpa.repository;

import com.software.hexagonal.demo.infrastructure.output.jpa.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<ClienteEntity, String> {
}
