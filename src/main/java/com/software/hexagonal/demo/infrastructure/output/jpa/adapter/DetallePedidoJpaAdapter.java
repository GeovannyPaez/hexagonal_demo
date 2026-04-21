package com.software.hexagonal.demo.infrastructure.output.jpa.adapter;

import com.software.hexagonal.demo.domain.model.DetallePedidoModel;
import com.software.hexagonal.demo.domain.spi.IDetallePedidoPersistencePort;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.DetallePedidoEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.PedidoEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.ProductoEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IDetallePedidoRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IPedidoRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IProductoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DetallePedidoJpaAdapter implements IDetallePedidoPersistencePort {

    private final IDetallePedidoRepository detallePedidoRepository;
    private final IPedidoRepository pedidoRepository;
    private final IProductoRepository productoRepository;

    public DetallePedidoJpaAdapter(IDetallePedidoRepository detallePedidoRepository,
                                   IPedidoRepository pedidoRepository,
                                   IProductoRepository productoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public void saveDetallePedido(DetallePedidoModel detallePedidoModel) {
        DetallePedidoEntity entity = toEntity(detallePedidoModel);
        detallePedidoRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateDetallePedido(DetallePedidoModel detallePedidoModel, Long id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id).orElseThrow();
        PedidoEntity pedidoEntity = pedidoRepository.findById(detallePedidoModel.getPedidoId()).orElseThrow();
        ProductoEntity productoEntity = productoRepository.findById(detallePedidoModel.getProductoId()).orElseThrow();

        entity.setPedido(pedidoEntity);
        entity.setProducto(productoEntity);
        entity.setCantidad(detallePedidoModel.getCantidad());
        entity.setPrecioUnitario(detallePedidoModel.getPrecioUnitario());
        entity.setSubtotal(detallePedidoModel.getSubtotal());

        detallePedidoRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteDetallePedido(Long id) {
        detallePedidoRepository.deleteById(id);
    }

    @Override
    public Optional<DetallePedidoModel> getDetallePedidoById(Long id) {
        return detallePedidoRepository.findById(id).map(this::toModel);
    }

    @Override
    public List<DetallePedidoModel> getAllDetallePedidos() {
        return detallePedidoRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DetallePedidoModel> getDetallesByPedidoId(Long pedidoId) {
        return detallePedidoRepository.findByPedido_Id(pedidoId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DetallePedidoModel> getDetallesByProductoId(Long productoId) {
        return detallePedidoRepository.findByProducto_Id(productoId).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return detallePedidoRepository.existsById(id);
    }

    private DetallePedidoEntity toEntity(DetallePedidoModel model) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(model.getPedidoId()).orElseThrow();
        ProductoEntity productoEntity = productoRepository.findById(model.getProductoId()).orElseThrow();

        DetallePedidoEntity entity = new DetallePedidoEntity();
        entity.setId(model.getId());
        entity.setPedido(pedidoEntity);
        entity.setProducto(productoEntity);
        entity.setCantidad(model.getCantidad());
        entity.setPrecioUnitario(model.getPrecioUnitario());
        entity.setSubtotal(model.getSubtotal());
        return entity;
    }

    private DetallePedidoModel toModel(DetallePedidoEntity entity) {
        DetallePedidoModel model = new DetallePedidoModel();
        model.setId(entity.getId());
        model.setPedidoId(entity.getPedido().getId());
        model.setProductoId(entity.getProducto().getId());
        model.setCantidad(entity.getCantidad());
        model.setPrecioUnitario(entity.getPrecioUnitario());
        model.setSubtotal(entity.getSubtotal());
        return model;
    }
}
