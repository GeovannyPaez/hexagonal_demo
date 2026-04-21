package com.software.hexagonal.demo.infrastructure.output.jpa.adapter;

import com.software.hexagonal.demo.domain.model.PedidoModel;
import com.software.hexagonal.demo.domain.spi.IPedidoPersistencePort;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.ClienteEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.PedidoEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IClienteRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IPedidoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final IClienteRepository clienteRepository;

    public PedidoJpaAdapter(IPedidoRepository pedidoRepository,
                            IClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public void savePedido(PedidoModel pedidoModel) {
        PedidoEntity entity = toEntity(pedidoModel);
        pedidoRepository.save(entity);
    }

    @Override
    @Transactional
    public void updatePedido(PedidoModel pedidoModel, Long id) {
        PedidoEntity entity = pedidoRepository.findById(id).orElseThrow();
        ClienteEntity clienteEntity = clienteRepository.findById(pedidoModel.getClienteId()).orElseThrow();

        entity.setFechaPedido(pedidoModel.getFechaPedido());
        entity.setEstado(pedidoModel.getEstado());
        entity.setCliente(clienteEntity);
        entity.setTotal(pedidoModel.getTotal());

        pedidoRepository.save(entity);
    }

    @Override
    @Transactional
    public void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Optional<PedidoModel> getPedidoById(Long id) {
        return pedidoRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<PedidoModel> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoModel> getPedidosByClienteId(String clienteId) {
        return pedidoRepository.findByCliente_Id(clienteId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return pedidoRepository.existsById(id);
    }

    private PedidoEntity toEntity(PedidoModel model) {
        ClienteEntity clienteEntity = clienteRepository.findById(model.getClienteId()).orElseThrow();

        PedidoEntity entity = new PedidoEntity();
        entity.setId(model.getId());
        entity.setFechaPedido(model.getFechaPedido());
        entity.setEstado(model.getEstado());
        entity.setCliente(clienteEntity);
        entity.setTotal(model.getTotal());
        return entity;
    }

    private PedidoModel toModel(PedidoEntity entity) {
        PedidoModel model = new PedidoModel();
        model.setId(entity.getId());
        model.setFechaPedido(entity.getFechaPedido());
        model.setEstado(entity.getEstado());
        model.setClienteId(entity.getCliente().getId());
        model.setTotal(entity.getTotal());
        return model;
    }
}
