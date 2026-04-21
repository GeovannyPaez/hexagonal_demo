package com.software.hexagonal.demo.infrastructure.output.jpa.repository;

import com.software.hexagonal.demo.infrastructure.output.jpa.entity.DetallePedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long> {

    List<DetallePedidoEntity> findByPedido_Id(Long pedidoId);
    List<DetallePedidoEntity> findByProducto_Id(Long productoId);
}
