package com.software.hexagonal.demo.domain.api;

import com.software.hexagonal.demo.domain.model.ProductoModel;

import java.util.List;

public interface IProductoServicePort {

    void saveProducto(ProductoModel productoModel);
    void updateProducto(ProductoModel productoModel, Long id);
    void deleteProducto(Long id);

    ProductoModel getProductoById(Long id);
    List<ProductoModel> getAllProductos();
    List<ProductoModel> getProductosByCategoriaId(Long categoriaId);
}
