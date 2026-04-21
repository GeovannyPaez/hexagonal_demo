package com.software.hexagonal.demo.domain.api;

import com.software.hexagonal.demo.domain.model.ClienteModel;

import java.util.List;

public interface IClienteServicePort {

    void saveCliente(ClienteModel clienteModel);
    void updateCliente(ClienteModel clienteModel, String id);
    void deleteCliente(String id);

    ClienteModel getClienteById(String id);
    List<ClienteModel> getAllClientes();
}
