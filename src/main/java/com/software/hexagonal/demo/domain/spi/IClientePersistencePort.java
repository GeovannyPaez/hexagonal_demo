package com.software.hexagonal.demo.domain.spi;

import com.software.hexagonal.demo.domain.model.ClienteModel;

import java.util.List;
import java.util.Optional;

public interface IClientePersistencePort {

    void saveCliente(ClienteModel clienteModel);
    void updateCliente(ClienteModel clienteModel, String id);
    void deleteCliente(String id);

    Optional<ClienteModel> getClienteById(String id);
    List<ClienteModel> getAllClientes();

    boolean existsById(String id);
}
