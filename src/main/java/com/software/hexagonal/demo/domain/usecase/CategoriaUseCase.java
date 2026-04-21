package com.software.hexagonal.demo.domain.usecase;

import com.software.hexagonal.demo.domain.api.ICategoriaServicePort;
import com.software.hexagonal.demo.domain.exception.DomainException;
import com.software.hexagonal.demo.domain.model.CategoriaModel;
import com.software.hexagonal.demo.domain.spi.ICategoriaPersistencePort;
import com.software.hexagonal.demo.domain.spi.IProductoPersistencePort;

import java.util.List;

public class CategoriaUseCase implements ICategoriaServicePort {

    private final ICategoriaPersistencePort categoriaPersistencePort;
    private final IProductoPersistencePort productoPersistencePort;

    public CategoriaUseCase(ICategoriaPersistencePort categoriaPersistencePort,
                            IProductoPersistencePort productoPersistencePort) {
        this.categoriaPersistencePort = categoriaPersistencePort;
        this.productoPersistencePort = productoPersistencePort;
    }

    @Override
    public void saveCategoria(CategoriaModel categoriaModel) {
        validateCategoria(categoriaModel);

        if (categoriaPersistencePort.existsByNombre(categoriaModel.getNombre())) {
            throw new DomainException("Ya existe una categoria con ese nombre");
        }

        categoriaPersistencePort.saveCategoria(categoriaModel);
    }

    @Override
    public void updateCategoria(CategoriaModel categoriaModel, Long id) {
        validateId(id);

        if (!categoriaPersistencePort.existsById(id)) {
            throw new DomainException("La categoria no existe");
        }

        validateCategoria(categoriaModel);
        categoriaPersistencePort.updateCategoria(categoriaModel, id);
    }

    @Override
    public void deleteCategoria(Long id) {
        validateId(id);

        if (!categoriaPersistencePort.existsById(id)) {
            throw new DomainException("La categoria no existe");
        }

        if (!productoPersistencePort.getProductosByCategoriaId(id).isEmpty()) {
            throw new DomainException("No se puede eliminar la categoria porque tiene productos asociados");
        }

        categoriaPersistencePort.deleteCategoria(id);
    }

    @Override
    public CategoriaModel getCategoriaById(Long id) {
        validateId(id);

        return categoriaPersistencePort.getCategoriaById(id)
                .orElseThrow(() -> new DomainException("Categoria no encontrada"));
    }

    @Override
    public List<CategoriaModel> getAllCategorias() {
        return categoriaPersistencePort.getAllCategorias();
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new DomainException("El id de la categoria es obligatorio");
        }
    }

    private void validateCategoria(CategoriaModel categoriaModel) {
        if (categoriaModel == null) {
            throw new DomainException("La categoria no puede ser nula");
        }

        if (categoriaModel.getNombre() == null || categoriaModel.getNombre().trim().isEmpty()) {
            throw new DomainException("El nombre de la categoria es obligatorio");
        }
    }
}
