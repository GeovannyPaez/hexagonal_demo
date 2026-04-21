package com.software.hexagonal.demo.application.dto.response;

public class ClienteResponseDto {

    private String id;
    private String nombre;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
