package com.software.hexagonal.demo.domain.usecase;

import com.software.hexagonal.demo.domain.api.IProductoServicePort;
import com.software.hexagonal.demo.domain.exception.DomainException;
import com.software.hexagonal.demo.domain.model.ProductoModel;
import com.software.hexagonal.demo.domain.spi.ICategoriaPersistencePort;
import com.software.hexagonal.demo.domain.spi.IDetallePedidoPersistencePort;
import com.software.hexagonal.demo.domain.spi.IProductoPersistencePort;

import java.math.BigDecimal;
import java.util.List;

public class ProductoUseCase implements IProductoServicePort {

    private final IProductoPersistencePort productoPersistencePort;
    private final ICategoriaPersistencePort categoriaPersistencePort;
    private final IDetallePedidoPersistencePort detallePedidoPersistencePort;

    public ProductoUseCase(IProductoPersistencePort productoPersistencePort,
                           ICategoriaPersistencePort categoriaPersistencePort,
                           IDetallePedidoPersistencePort detallePedidoPersistencePort) {
        this.productoPersistencePort = productoPersistencePort;
        this.categoriaPersistencePort = categoriaPersistencePort;
        this.detallePedidoPersistencePort = detallePedidoPersistencePort;
    }

    @Override
    public void saveProducto(ProductoModel productoModel) {
        validateProducto(productoModel);

        if (!categoriaPersistencePort.existsById(productoModel.getCategoriaId())) {
            throw new DomainException("La categoria asociada no existe");
        }

        if (productoPersistencePort.existsByNombre(productoModel.getNombre())) {
            throw new DomainException("Ya existe un producto con ese nombre");
        }

        productoPersistencePort.saveProducto(productoModel);
    }

    @Override
    public void updateProducto(ProductoModel productoModel, Long id) {
        validateId(id);

        if (!productoPersistencePort.existsById(id)) {
            throw new DomainException("El producto no existe");
        }

        validateProducto(productoModel);

        if (!categoriaPersistencePort.existsById(productoModel.getCategoriaId())) {
            throw new DomainException("La categoria asociada no existe");
        }

        productoPersistencePort.updateProducto(productoModel, id);
    }

    @Override
    public void deleteProducto(Long id) {
        validateId(id);

        if (!productoPersistencePort.existsById(id)) {
            throw new DomainException("El producto no existe");
        }

        if (!detallePedidoPersistencePort.getDetallesByProductoId(id).isEmpty()) {
            throw new DomainException("No se puede eliminar el producto porque esta asociado a detalles de pedido");
        }

        productoPersistencePort.deleteProducto(id);
    }

    @Override
    public ProductoModel getProductoById(Long id) {
        validateId(id);

        return productoPersistencePort.getProductoById(id)
                .orElseThrow(() -> new DomainException("Producto no encontrado"));
    }

    @Override
    public List<ProductoModel> getAllProductos() {
        return productoPersistencePort.getAllProductos();
    }

    @Override
    public List<ProductoModel> getProductosByCategoriaId(Long categoriaId) {
        validateId(categoriaId);

        if (!categoriaPersistencePort.existsById(categoriaId)) {
            throw new DomainException("La categoria no existe");
        }

        return productoPersistencePort.getProductosByCategoriaId(categoriaId);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new DomainException("El id del producto es obligatorio");
        }
    }

    private void validateProducto(ProductoModel productoModel) {
        if (productoModel == null) {
            throw new DomainException("El producto no puede ser nulo");
        }

        if (productoModel.getNombre() == null || productoModel.getNombre().trim().isEmpty()) {
            throw new DomainException("El nombre del producto es obligatorio");
        }

        if (productoModel.getPrecio() == null) {
            throw new DomainException("El precio del producto es obligatorio");
        }

        if (productoModel.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("El precio del producto debe ser mayor a cero");
        }

        if (productoModel.getStock() == null) {
            throw new DomainException("El stock del producto es obligatorio");
        }

        if (productoModel.getStock() < 0) {
            throw new DomainException("El stock del producto no puede ser negativo");
        }

        if (productoModel.getCategoriaId() == null) {
            throw new DomainException("La categoria del producto es obligatoria");
        }
    }
}
