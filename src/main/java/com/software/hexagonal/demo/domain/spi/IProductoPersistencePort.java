package com.software.hexagonal.demo.domain.spi;

import com.software.hexagonal.demo.domain.model.ProductoModel;

import java.util.List;
import java.util.Optional;

public interface IProductoPersistencePort {

    void saveProducto(ProductoModel productoModel);
    void updateProducto(ProductoModel productoModel, Long id);
    void deleteProducto(Long id);

    Optional<ProductoModel> getProductoById(Long id);
    List<ProductoModel> getAllProductos();
    List<ProductoModel> getProductosByCategoriaId(Long categoriaId);

    boolean existsByNombre(String nombre);
    boolean existsById(Long id);
}
