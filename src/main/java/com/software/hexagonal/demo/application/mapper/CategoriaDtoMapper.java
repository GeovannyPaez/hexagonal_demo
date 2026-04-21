package com.software.hexagonal.demo.application.mapper;

import com.software.hexagonal.demo.application.dto.request.CategoriaRequestDto;
import com.software.hexagonal.demo.application.dto.response.CategoriaResponseDto;
import com.software.hexagonal.demo.domain.model.CategoriaModel;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaDtoMapper {

    public static CategoriaModel toModel(CategoriaRequestDto dto) {
        CategoriaModel model = new CategoriaModel();
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());
        return model;
    }

    public static CategoriaResponseDto toResponse(CategoriaModel model) {
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());
        return dto;
    }

    public static List<CategoriaResponseDto> toResponseList(List<CategoriaModel> models) {
        return models.stream().map(CategoriaDtoMapper::toResponse).collect(Collectors.toList());
    }
}
