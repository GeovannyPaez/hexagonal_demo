package com.software.hexagonal.demo.application.mapper;

import com.software.hexagonal.demo.application.dto.request.DetallePedidoRequestDto;
import com.software.hexagonal.demo.application.dto.response.DetallePedidoResponseDto;
import com.software.hexagonal.demo.domain.model.DetallePedidoModel;

import java.util.List;
import java.util.stream.Collectors;

public class DetallePedidoDtoMapper {

    public static DetallePedidoModel toModel(DetallePedidoRequestDto dto) {
        DetallePedidoModel model = new DetallePedidoModel();
        model.setPedidoId(dto.getPedidoId());
        model.setProductoId(dto.getProductoId());
        model.setCantidad(dto.getCantidad());
        model.setPrecioUnitario(dto.getPrecioUnitario());
        model.setSubtotal(dto.getSubtotal());
        return model;
    }

    public static DetallePedidoResponseDto toResponse(DetallePedidoModel model) {
        DetallePedidoResponseDto dto = new DetallePedidoResponseDto();
        dto.setId(model.getId());
        dto.setPedidoId(model.getPedidoId());
        dto.setProductoId(model.getProductoId());
        dto.setCantidad(model.getCantidad());
        dto.setPrecioUnitario(model.getPrecioUnitario());
        dto.setSubtotal(model.getSubtotal());
        return dto;
    }

    public static List<DetallePedidoResponseDto> toResponseList(List<DetallePedidoModel> models) {
        return models.stream().map(DetallePedidoDtoMapper::toResponse).collect(Collectors.toList());
    }
}
