package com.software.hexagonal.demo.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoModel {

    private Long id;
    private LocalDate fechaPedido;
    private String estado;
    private String clienteId;
    private BigDecimal total;
    private List<DetallePedidoModel> detalles;

    public PedidoModel() {
        this.detalles = new ArrayList<>();
    }

    public PedidoModel(Long id,
                       LocalDate fechaPedido,
                       String estado,
                       String clienteId,
                       BigDecimal total,
                       List<DetallePedidoModel> detalles) {
        this.id = id;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.clienteId = clienteId;
        this.total = total;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public String getClienteId() {
        return clienteId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<DetallePedidoModel> getDetalles() {
        return detalles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setDetalles(List<DetallePedidoModel> detalles) {
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }
}
