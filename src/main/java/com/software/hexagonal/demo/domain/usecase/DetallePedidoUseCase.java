package com.software.hexagonal.demo.domain.usecase;

import com.software.hexagonal.demo.domain.api.IDetallePedidoServicePort;
import com.software.hexagonal.demo.domain.exception.DomainException;
import com.software.hexagonal.demo.domain.model.DetallePedidoModel;
import com.software.hexagonal.demo.domain.spi.IDetallePedidoPersistencePort;
import com.software.hexagonal.demo.domain.spi.IPedidoPersistencePort;
import com.software.hexagonal.demo.domain.spi.IProductoPersistencePort;

import java.math.BigDecimal;
import java.util.List;

public class DetallePedidoUseCase implements IDetallePedidoServicePort {

    private final IDetallePedidoPersistencePort detallePedidoPersistencePort;
    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IProductoPersistencePort productoPersistencePort;

    public DetallePedidoUseCase(IDetallePedidoPersistencePort detallePedidoPersistencePort,
                                IPedidoPersistencePort pedidoPersistencePort,
                                IProductoPersistencePort productoPersistencePort) {
        this.detallePedidoPersistencePort = detallePedidoPersistencePort;
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.productoPersistencePort = productoPersistencePort;
    }

    @Override
    public void saveDetallePedido(DetallePedidoModel detallePedidoModel) {
        validateDetalle(detallePedidoModel);
        validateRelationships(detallePedidoModel);

        detallePedidoPersistencePort.saveDetallePedido(detallePedidoModel);
    }

    @Override
    public void updateDetallePedido(DetallePedidoModel detallePedidoModel, Long id) {
        validateId(id);

        if (!detallePedidoPersistencePort.existsById(id)) {
            throw new DomainException("El detalle del pedido no existe");
        }

        validateDetalle(detallePedidoModel);
        validateRelationships(detallePedidoModel);

        detallePedidoPersistencePort.updateDetallePedido(detallePedidoModel, id);
    }

    @Override
    public void deleteDetallePedido(Long id) {
        validateId(id);

        if (!detallePedidoPersistencePort.existsById(id)) {
            throw new DomainException("El detalle del pedido no existe");
        }

        detallePedidoPersistencePort.deleteDetallePedido(id);
    }

    @Override
    public DetallePedidoModel getDetallePedidoById(Long id) {
        validateId(id);

        return detallePedidoPersistencePort.getDetallePedidoById(id)
                .orElseThrow(() -> new DomainException("Detalle de pedido no encontrado"));
    }

    @Override
    public List<DetallePedidoModel> getAllDetallePedidos() {
        return detallePedidoPersistencePort.getAllDetallePedidos();
    }

    @Override
    public List<DetallePedidoModel> getDetallesByPedidoId(Long pedidoId) {
        validateId(pedidoId);

        if (!pedidoPersistencePort.existsById(pedidoId)) {
            throw new DomainException("El pedido no existe");
        }

        return detallePedidoPersistencePort.getDetallesByPedidoId(pedidoId);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new DomainException("El id del detalle de pedido es obligatorio");
        }
    }

    private void validateDetalle(DetallePedidoModel detallePedidoModel) {
        if (detallePedidoModel == null) {
            throw new DomainException("El detalle del pedido no puede ser nulo");
        }

        if (detallePedidoModel.getPedidoId() == null) {
            throw new DomainException("El pedido asociado es obligatorio");
        }

        if (detallePedidoModel.getProductoId() == null) {
            throw new DomainException("El producto asociado es obligatorio");
        }

        if (detallePedidoModel.getCantidad() == null) {
            throw new DomainException("La cantidad es obligatoria");
        }

        if (detallePedidoModel.getCantidad() <= 0) {
            throw new DomainException("La cantidad debe ser mayor a cero");
        }

        if (detallePedidoModel.getPrecioUnitario() == null) {
            throw new DomainException("El precio unitario es obligatorio");
        }

        if (detallePedidoModel.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("El precio unitario debe ser mayor a cero");
        }

        BigDecimal subtotalCalculado = detallePedidoModel.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detallePedidoModel.getCantidad()));

        if (detallePedidoModel.getSubtotal() == null) {
            detallePedidoModel.setSubtotal(subtotalCalculado);
            return;
        }

        if (detallePedidoModel.getSubtotal().compareTo(subtotalCalculado) != 0) {
            throw new DomainException("El subtotal no coincide con cantidad por precio unitario");
        }
    }

    private void validateRelationships(DetallePedidoModel detallePedidoModel) {
        if (!pedidoPersistencePort.existsById(detallePedidoModel.getPedidoId())) {
            throw new DomainException("El pedido asociado no existe");
        }

        if (!productoPersistencePort.existsById(detallePedidoModel.getProductoId())) {
            throw new DomainException("El producto asociado no existe");
        }
    }
}
