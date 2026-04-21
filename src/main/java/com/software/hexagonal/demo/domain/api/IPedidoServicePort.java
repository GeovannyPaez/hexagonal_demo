package com.software.hexagonal.demo.domain.api;

import com.software.hexagonal.demo.domain.model.PedidoModel;

import java.util.List;

public interface IPedidoServicePort {

    void savePedido(PedidoModel pedidoModel);
    void updatePedido(PedidoModel pedidoModel, Long id);
    void deletePedido(Long id);

    PedidoModel getPedidoById(Long id);
    List<PedidoModel> getAllPedidos();
    List<PedidoModel> getPedidosByClienteId(String clienteId);
}
