package com.software.hexagonal.demo.domain.spi;

import com.software.hexagonal.demo.domain.model.DetallePedidoModel;

import java.util.List;
import java.util.Optional;

public interface IDetallePedidoPersistencePort {

    void saveDetallePedido(DetallePedidoModel detallePedidoModel);
    void updateDetallePedido(DetallePedidoModel detallePedidoModel, Long id);
    void deleteDetallePedido(Long id);

    Optional<DetallePedidoModel> getDetallePedidoById(Long id);
    List<DetallePedidoModel> getAllDetallePedidos();
    List<DetallePedidoModel> getDetallesByPedidoId(Long pedidoId);
    List<DetallePedidoModel> getDetallesByProductoId(Long productoId);

    boolean existsById(Long id);
}
