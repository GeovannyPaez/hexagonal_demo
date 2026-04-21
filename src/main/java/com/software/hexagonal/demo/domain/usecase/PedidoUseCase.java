package com.software.hexagonal.demo.domain.usecase;

import com.software.hexagonal.demo.domain.api.IPedidoServicePort;
import com.software.hexagonal.demo.domain.exception.DomainException;
import com.software.hexagonal.demo.domain.model.PedidoModel;
import com.software.hexagonal.demo.domain.spi.IClientePersistencePort;
import com.software.hexagonal.demo.domain.spi.IPedidoPersistencePort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IClientePersistencePort clientePersistencePort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                         IClientePersistencePort clientePersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.clientePersistencePort = clientePersistencePort;
    }

    @Override
    public void savePedido(PedidoModel pedidoModel) {
        validatePedido(pedidoModel);

        if (!clientePersistencePort.existsById(pedidoModel.getClienteId())) {
            throw new DomainException("No se puede registrar el pedido porque el cliente no existe");
        }

        pedidoPersistencePort.savePedido(pedidoModel);
    }

    @Override
    public void updatePedido(PedidoModel pedidoModel, Long id) {
        validateId(id);

        if (!pedidoPersistencePort.existsById(id)) {
            throw new DomainException("El pedido no existe");
        }

        validatePedido(pedidoModel);

        if (!clientePersistencePort.existsById(pedidoModel.getClienteId())) {
            throw new DomainException("El cliente asociado no existe");
        }

        pedidoPersistencePort.updatePedido(pedidoModel, id);
    }

    @Override
    public void deletePedido(Long id) {
        validateId(id);

        if (!pedidoPersistencePort.existsById(id)) {
            throw new DomainException("El pedido no existe");
        }

        pedidoPersistencePort.deletePedido(id);
    }

    @Override
    public PedidoModel getPedidoById(Long id) {
        validateId(id);

        return pedidoPersistencePort.getPedidoById(id)
                .orElseThrow(() -> new DomainException("Pedido no encontrado"));
    }

    @Override
    public List<PedidoModel> getAllPedidos() {
        return pedidoPersistencePort.getAllPedidos();
    }

    @Override
    public List<PedidoModel> getPedidosByClienteId(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) {
            throw new DomainException("El id del cliente es obligatorio");
        }

        if (!clientePersistencePort.existsById(clienteId)) {
            throw new DomainException("El cliente no existe");
        }

        return pedidoPersistencePort.getPedidosByClienteId(clienteId);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new DomainException("El id del pedido es obligatorio");
        }
    }

    private void validatePedido(PedidoModel pedidoModel) {
        if (pedidoModel == null) {
            throw new DomainException("El pedido no puede ser nulo");
        }

        if (pedidoModel.getFechaPedido() == null) {
            throw new DomainException("La fecha del pedido es obligatoria");
        }

        if (pedidoModel.getFechaPedido().isAfter(LocalDate.now())) {
            throw new DomainException("La fecha del pedido no puede ser futura");
        }

        if (pedidoModel.getEstado() == null || pedidoModel.getEstado().trim().isEmpty()) {
            throw new DomainException("El estado del pedido es obligatorio");
        }

        if (pedidoModel.getClienteId() == null || pedidoModel.getClienteId().trim().isEmpty()) {
            throw new DomainException("El cliente asociado es obligatorio");
        }

        if (pedidoModel.getTotal() == null) {
            throw new DomainException("El total del pedido es obligatorio");
        }

        if (pedidoModel.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("El total del pedido no puede ser negativo");
        }
    }
}
