package com.software.hexagonal.demo.domain.api;

import com.software.hexagonal.demo.domain.model.DetallePedidoModel;

import java.util.List;

public interface IDetallePedidoServicePort {

    void saveDetallePedido(DetallePedidoModel detallePedidoModel);
    void updateDetallePedido(DetallePedidoModel detallePedidoModel, Long id);
    void deleteDetallePedido(Long id);

    DetallePedidoModel getDetallePedidoById(Long id);
    List<DetallePedidoModel> getAllDetallePedidos();
    List<DetallePedidoModel> getDetallesByPedidoId(Long pedidoId);
}
