package com.software.hexagonal.demo.application.mapper;

import com.software.hexagonal.demo.application.dto.request.PedidoRequestDto;
import com.software.hexagonal.demo.application.dto.response.PedidoResponseDto;
import com.software.hexagonal.demo.domain.model.PedidoModel;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoDtoMapper {

    public static PedidoModel toModel(PedidoRequestDto dto) {
        PedidoModel model = new PedidoModel();
        model.setFechaPedido(dto.getFechaPedido());
        model.setEstado(dto.getEstado());
        model.setClienteId(dto.getClienteId());
        model.setTotal(dto.getTotal());
        return model;
    }

    public static PedidoResponseDto toResponse(PedidoModel model) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.setId(model.getId());
        dto.setFechaPedido(model.getFechaPedido());
        dto.setEstado(model.getEstado());
        dto.setClienteId(model.getClienteId());
        dto.setTotal(model.getTotal());
        return dto;
    }

    public static List<PedidoResponseDto> toResponseList(List<PedidoModel> models) {
        return models.stream().map(PedidoDtoMapper::toResponse).collect(Collectors.toList());
    }
}
