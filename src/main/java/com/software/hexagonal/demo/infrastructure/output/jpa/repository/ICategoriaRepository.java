package com.software.hexagonal.demo.infrastructure.output.jpa.repository;

import com.software.hexagonal.demo.infrastructure.output.jpa.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    boolean existsByNombre(String nombre);
}
