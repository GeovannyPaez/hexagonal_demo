package com.software.hexagonal.demo.infrastructure.output.jpa.adapter;

import com.software.hexagonal.demo.domain.model.CategoriaModel;
import com.software.hexagonal.demo.domain.spi.ICategoriaPersistencePort;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.CategoriaEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.ICategoriaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoriaJpaAdapter implements ICategoriaPersistencePort {

    private final ICategoriaRepository categoriaRepository;

    public CategoriaJpaAdapter(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public void saveCategoria(CategoriaModel categoriaModel) {
        CategoriaEntity entity = toEntity(categoriaModel);
        categoriaRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateCategoria(CategoriaModel categoriaModel, Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id).orElseThrow();
        entity.setNombre(categoriaModel.getNombre());
        entity.setDescripcion(categoriaModel.getDescripcion());
        categoriaRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public Optional<CategoriaModel> getCategoriaById(Long id) {
        return categoriaRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<CategoriaModel> getAllCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existsById(Long id) {
        return categoriaRepository.existsById(id);
    }

    private CategoriaEntity toEntity(CategoriaModel model) {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        return entity;
    }

    private CategoriaModel toModel(CategoriaEntity entity) {
        CategoriaModel model = new CategoriaModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setDescripcion(entity.getDescripcion());
        return model;
    }
}
