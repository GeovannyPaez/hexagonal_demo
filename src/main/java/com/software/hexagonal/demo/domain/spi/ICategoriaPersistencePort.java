package com.software.hexagonal.demo.domain.spi;

import com.software.hexagonal.demo.domain.model.CategoriaModel;

import java.util.List;
import java.util.Optional;

public interface ICategoriaPersistencePort {

    void saveCategoria(CategoriaModel categoriaModel);
    void updateCategoria(CategoriaModel categoriaModel, Long id);
    void deleteCategoria(Long id);

    Optional<CategoriaModel> getCategoriaById(Long id);
    List<CategoriaModel> getAllCategorias();

    boolean existsByNombre(String nombre);
    boolean existsById(Long id);
}
