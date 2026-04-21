package com.software.hexagonal.demo.infrastructure.output.jpa.adapter;

import com.software.hexagonal.demo.domain.model.ClienteModel;
import com.software.hexagonal.demo.domain.spi.IClientePersistencePort;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.ClienteEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IClienteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteJpaAdapter implements IClientePersistencePort {

    private final IClienteRepository clienteRepository;

    public ClienteJpaAdapter(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public void saveCliente(ClienteModel clienteModel) {
        ClienteEntity entity = toEntity(clienteModel);
        clienteRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateCliente(ClienteModel clienteModel, String id) {
        ClienteEntity entity = clienteRepository.findById(id).orElseThrow();
        entity.setNombre(clienteModel.getNombre());
        clienteRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteCliente(String id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Optional<ClienteModel> getClienteById(String id) {
        return clienteRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<ClienteModel> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return clienteRepository.existsById(id);
    }

    private ClienteEntity toEntity(ClienteModel model) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        return entity;
    }

    private ClienteModel toModel(ClienteEntity entity) {
        ClienteModel model = new ClienteModel();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        return model;
    }
}
