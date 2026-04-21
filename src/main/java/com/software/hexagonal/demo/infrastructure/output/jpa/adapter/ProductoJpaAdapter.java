package com.software.hexagonal.demo.infrastructure.output.jpa.adapter;

import com.software.hexagonal.demo.domain.model.ProductoModel;
import com.software.hexagonal.demo.domain.spi.IProductoPersistencePort;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.CategoriaEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.ProductoEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.ICategoriaRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IProductoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductoJpaAdapter implements IProductoPersistencePort {

    private final IProductoRepository productoRepository;
    private final ICategoriaRepository categoriaRepository;

    public ProductoJpaAdapter(IProductoRepository productoRepository,
                              ICategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public void saveProducto(ProductoModel productoModel) {
        ProductoEntity entity = toEntity(productoModel);
        productoRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateProducto(ProductoModel productoModel, Long id) {
        ProductoEntity entity = productoRepository.findById(id).orElseThrow();
        CategoriaEntity categoriaEntity = categoriaRepository.findById(productoModel.getCategoriaId()).orElseThrow();

        entity.setNombre(productoModel.getNombre());
        entity.setDescripcion(productoModel.getDescripcion());
        entity.setPrecio(productoModel.getPrecio());
        entity.setStock(productoModel.getStock());
        entity.setCategoria(categoriaEntity);

        productoRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Optional<ProductoModel> getProductoById(Long id) {
        return productoRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<ProductoModel> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoModel> getProductosByCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return productoRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }

    private ProductoEntity toEntity(ProductoModel model) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(model.getCategoriaId()).orElseThrow();

        ProductoEntity entity = new ProductoEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setPrecio(model.getPrecio());
        entity.setStock(model.getStock());
        entity.setCategoria(categoriaEntity);
        return entity;
    }

    private ProductoModel toModel(ProductoEntity entity) {
        ProductoModel model = new ProductoModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setDescripcion(entity.getDescripcion());
        model.setPrecio(entity.getPrecio());
        model.setStock(entity.getStock());
        model.setCategoriaId(entity.getCategoria().getId());
        return model;
    }
}
