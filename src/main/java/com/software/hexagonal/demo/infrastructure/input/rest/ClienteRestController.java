package com.software.hexagonal.demo.infrastructure.input.rest;

import com.software.hexagonal.demo.application.dto.request.ClienteRequestDto;
import com.software.hexagonal.demo.application.dto.response.ClienteResponseDto;
import com.software.hexagonal.demo.application.mapper.ClienteDtoMapper;
import com.software.hexagonal.demo.domain.api.IClienteServicePort;
import com.software.hexagonal.demo.domain.model.ClienteModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

    private final IClienteServicePort clienteServicePort;

    public ClienteRestController(IClienteServicePort clienteServicePort) {
        this.clienteServicePort = clienteServicePort;
    }

    @PostMapping
    public ResponseEntity<String> saveCliente(@RequestBody ClienteRequestDto requestDto) {
        ClienteModel model = ClienteDtoMapper.toModel(requestDto);
        clienteServicePort.saveCliente(model);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCliente(@RequestBody ClienteRequestDto requestDto,
                                                @PathVariable String id) {
        ClienteModel model = ClienteDtoMapper.toModel(requestDto);
        model.setId(id);
        clienteServicePort.updateCliente(model, id);
        return ResponseEntity.ok("Cliente actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable String id) {
        clienteServicePort.deleteCliente(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getClienteById(@PathVariable String id) {
        ClienteModel model = clienteServicePort.getClienteById(id);
        return ResponseEntity.ok(ClienteDtoMapper.toResponse(model));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getAllClientes() {
        return ResponseEntity.ok(ClienteDtoMapper.toResponseList(clienteServicePort.getAllClientes()));
    }
}
