package com.software.hexagonal.demo.application.mapper;

import com.software.hexagonal.demo.application.dto.request.ClienteRequestDto;
import com.software.hexagonal.demo.application.dto.response.ClienteResponseDto;
import com.software.hexagonal.demo.domain.model.ClienteModel;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteDtoMapper {

    public static ClienteModel toModel(ClienteRequestDto dto) {
        ClienteModel model = new ClienteModel();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        return model;
    }

    public static ClienteResponseDto toResponse(ClienteModel model) {
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        return dto;
    }

    public static List<ClienteResponseDto> toResponseList(List<ClienteModel> models) {
        return models.stream().map(ClienteDtoMapper::toResponse).collect(Collectors.toList());
    }
}
