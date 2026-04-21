package com.software.hexagonal.demo.domain.spi;

import com.software.hexagonal.demo.domain.model.PedidoModel;

import java.util.List;
import java.util.Optional;

public interface IPedidoPersistencePort {

    void savePedido(PedidoModel pedidoModel);
    void updatePedido(PedidoModel pedidoModel, Long id);
    void deletePedido(Long id);

    Optional<PedidoModel> getPedidoById(Long id);
    List<PedidoModel> getAllPedidos();
    List<PedidoModel> getPedidosByClienteId(String clienteId);

    boolean existsById(Long id);
}
