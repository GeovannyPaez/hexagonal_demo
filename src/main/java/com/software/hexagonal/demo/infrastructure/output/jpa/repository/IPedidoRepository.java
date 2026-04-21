package com.software.hexagonal.demo.infrastructure.output.jpa.repository;

import com.software.hexagonal.demo.infrastructure.output.jpa.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByCliente_Id(String clienteId);
}
