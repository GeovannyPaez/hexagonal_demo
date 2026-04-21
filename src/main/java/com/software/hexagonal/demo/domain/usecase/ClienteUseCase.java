package com.software.hexagonal.demo.domain.usecase;

import com.software.hexagonal.demo.domain.api.IClienteServicePort;
import com.software.hexagonal.demo.domain.exception.DomainException;
import com.software.hexagonal.demo.domain.model.ClienteModel;
import com.software.hexagonal.demo.domain.spi.IClientePersistencePort;
import com.software.hexagonal.demo.domain.spi.IPedidoPersistencePort;

import java.util.List;

public class ClienteUseCase implements IClienteServicePort {

    private final IClientePersistencePort clientePersistencePort;
    private final IPedidoPersistencePort pedidoPersistencePort;

    public ClienteUseCase(IClientePersistencePort clientePersistencePort,
                          IPedidoPersistencePort pedidoPersistencePort) {
        this.clientePersistencePort = clientePersistencePort;
        this.pedidoPersistencePort = pedidoPersistencePort;
    }

    @Override
    public void saveCliente(ClienteModel clienteModel) {
        validateCliente(clienteModel);

        if (clientePersistencePort.existsById(clienteModel.getId())) {
            throw new DomainException("Ya existe un cliente con ese id");
        }

        clientePersistencePort.saveCliente(clienteModel);
    }

    @Override
    public void updateCliente(ClienteModel clienteModel, String id) {
        validateId(id);

        if (!clientePersistencePort.existsById(id)) {
            throw new DomainException("El cliente no existe");
        }

        validateCliente(clienteModel);
        clientePersistencePort.updateCliente(clienteModel, id);
    }

    @Override
    public void deleteCliente(String id) {
        validateId(id);

        if (!clientePersistencePort.existsById(id)) {
            throw new DomainException("El cliente no existe");
        }

        if (!pedidoPersistencePort.getPedidosByClienteId(id).isEmpty()) {
            throw new DomainException("No se puede eliminar el cliente porque tiene pedidos asociados");
        }

        clientePersistencePort.deleteCliente(id);
    }

    @Override
    public ClienteModel getClienteById(String id) {
        validateId(id);

        return clientePersistencePort.getClienteById(id)
                .orElseThrow(() -> new DomainException("Cliente no encontrado"));
    }

    @Override
    public List<ClienteModel> getAllClientes() {
        return clientePersistencePort.getAllClientes();
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new DomainException("El id del cliente es obligatorio");
        }
    }

    private void validateCliente(ClienteModel clienteModel) {
        if (clienteModel == null) {
            throw new DomainException("El cliente no puede ser nulo");
        }

        validateId(clienteModel.getId());

        if (clienteModel.getNombre() == null || clienteModel.getNombre().trim().isEmpty()) {
            throw new DomainException("El nombre del cliente es obligatorio");
        }
    }
}
