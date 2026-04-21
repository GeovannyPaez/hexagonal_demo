package com.software.hexagonal.demo.infrastructure.input.rest;

import com.software.hexagonal.demo.application.dto.request.PedidoRequestDto;
import com.software.hexagonal.demo.application.dto.response.PedidoResponseDto;
import com.software.hexagonal.demo.application.mapper.PedidoDtoMapper;
import com.software.hexagonal.demo.domain.api.IPedidoServicePort;
import com.software.hexagonal.demo.domain.model.PedidoModel;
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
@RequestMapping("/pedidos")
public class PedidoRestController {

    private final IPedidoServicePort pedidoServicePort;

    public PedidoRestController(IPedidoServicePort pedidoServicePort) {
        this.pedidoServicePort = pedidoServicePort;
    }

    @PostMapping
    public ResponseEntity<String> savePedido(@RequestBody PedidoRequestDto requestDto) {
        PedidoModel model = PedidoDtoMapper.toModel(requestDto);
        pedidoServicePort.savePedido(model);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePedido(@RequestBody PedidoRequestDto requestDto,
                                               @PathVariable Long id) {
        PedidoModel model = PedidoDtoMapper.toModel(requestDto);
        pedidoServicePort.updatePedido(model, id);
        return ResponseEntity.ok("Pedido actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        pedidoServicePort.deletePedido(id);
        return ResponseEntity.ok("Pedido eliminado correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long id) {
        PedidoModel model = pedidoServicePort.getPedidoById(id);
        return ResponseEntity.ok(PedidoDtoMapper.toResponse(model));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        return ResponseEntity.ok(PedidoDtoMapper.toResponseList(pedidoServicePort.getAllPedidos()));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDto>> getByCliente(@PathVariable String clienteId) {
        return ResponseEntity.ok(PedidoDtoMapper.toResponseList(pedidoServicePort.getPedidosByClienteId(clienteId)));
    }
}
