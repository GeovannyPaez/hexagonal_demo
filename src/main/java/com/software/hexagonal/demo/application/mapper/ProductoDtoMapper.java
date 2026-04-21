package com.software.hexagonal.demo.application.mapper;

import com.software.hexagonal.demo.application.dto.request.ProductoRequestDto;
import com.software.hexagonal.demo.application.dto.response.ProductoResponseDto;
import com.software.hexagonal.demo.domain.model.ProductoModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoDtoMapper {

    public static ProductoModel toModel(ProductoRequestDto dto) {
        ProductoModel model = new ProductoModel();
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        model.setPrecio(dto.getPrecio());
        model.setStock(dto.getStock());
        model.setCategoriaId(dto.getCategoriaId());
        return model;
    }

    public static ProductoResponseDto toResponse(ProductoModel model) {
        ProductoResponseDto dto = new ProductoResponseDto();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        dto.setPrecio(model.getPrecio());
        dto.setStock(model.getStock());
        dto.setCategoriaId(model.getCategoriaId());
        return dto;
    }

    public static List<ProductoResponseDto> toResponseList(List<ProductoModel> models) {
        return models.stream().map(ProductoDtoMapper::toResponse).collect(Collectors.toList());
    }
}
