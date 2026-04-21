package com.software.hexagonal.demo.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PedidoResponseDto {

    private Long id;
    private LocalDate fechaPedido;
    private String estado;
    private String clienteId;
    private BigDecimal total;

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
}
