package com.software.hexagonal.demo.domain.api;

import com.software.hexagonal.demo.domain.model.CategoriaModel;

import java.util.List;

public interface ICategoriaServicePort {

    void saveCategoria(CategoriaModel categoriaModel);
    void updateCategoria(CategoriaModel categoriaModel, Long id);
    void deleteCategoria(Long id);

    CategoriaModel getCategoriaById(Long id);
    List<CategoriaModel> getAllCategorias();
}
