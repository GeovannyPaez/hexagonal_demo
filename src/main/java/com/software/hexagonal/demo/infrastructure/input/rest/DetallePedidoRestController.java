package com.software.hexagonal.demo.infrastructure.input.rest;

import com.software.hexagonal.demo.application.dto.request.DetallePedidoRequestDto;
import com.software.hexagonal.demo.application.dto.response.DetallePedidoResponseDto;
import com.software.hexagonal.demo.application.mapper.DetallePedidoDtoMapper;
import com.software.hexagonal.demo.domain.api.IDetallePedidoServicePort;
import com.software.hexagonal.demo.domain.model.DetallePedidoModel;
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
@RequestMapping("/detalles-pedido")
public class DetallePedidoRestController {

    private final IDetallePedidoServicePort detallePedidoServicePort;

    public DetallePedidoRestController(IDetallePedidoServicePort detallePedidoServicePort) {
        this.detallePedidoServicePort = detallePedidoServicePort;
    }

    @PostMapping
    public ResponseEntity<String> saveDetallePedido(@RequestBody DetallePedidoRequestDto requestDto) {
        DetallePedidoModel model = DetallePedidoDtoMapper.toModel(requestDto);
        detallePedidoServicePort.saveDetallePedido(model);
        return ResponseEntity.status(HttpStatus.CREATED).body("Detalle de pedido creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDetallePedido(@RequestBody DetallePedidoRequestDto requestDto,
                                                      @PathVariable Long id) {
        DetallePedidoModel model = DetallePedidoDtoMapper.toModel(requestDto);
        detallePedidoServicePort.updateDetallePedido(model, id);
        return ResponseEntity.ok("Detalle de pedido actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetallePedido(@PathVariable Long id) {
        detallePedidoServicePort.deleteDetallePedido(id);
        return ResponseEntity.ok("Detalle de pedido eliminado correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDto> getDetallePedidoById(@PathVariable Long id) {
        DetallePedidoModel model = detallePedidoServicePort.getDetallePedidoById(id);
        return ResponseEntity.ok(DetallePedidoDtoMapper.toResponse(model));
    }

    @GetMapping
    public ResponseEntity<List<DetallePedidoResponseDto>> getAllDetallePedidos() {
        return ResponseEntity.ok(
                DetallePedidoDtoMapper.toResponseList(detallePedidoServicePort.getAllDetallePedidos())
        );
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallePedidoResponseDto>> getByPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(
                DetallePedidoDtoMapper.toResponseList(detallePedidoServicePort.getDetallesByPedidoId(pedidoId))
        );
    }
}
