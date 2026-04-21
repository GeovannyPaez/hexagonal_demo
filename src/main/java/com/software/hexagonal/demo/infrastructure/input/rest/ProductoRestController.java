package com.software.hexagonal.demo.infrastructure.input.rest;

import com.software.hexagonal.demo.application.dto.request.ProductoRequestDto;
import com.software.hexagonal.demo.application.dto.response.ProductoResponseDto;
import com.software.hexagonal.demo.application.mapper.ProductoDtoMapper;
import com.software.hexagonal.demo.domain.api.IProductoServicePort;
import com.software.hexagonal.demo.domain.model.ProductoModel;
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
@RequestMapping("/productos")
public class ProductoRestController {

    private final IProductoServicePort productoServicePort;

    public ProductoRestController(IProductoServicePort productoServicePort) {
        this.productoServicePort = productoServicePort;
    }

    @PostMapping
    public ResponseEntity<String> saveProducto(@RequestBody ProductoRequestDto requestDto) {
        ProductoModel model = ProductoDtoMapper.toModel(requestDto);
        productoServicePort.saveProducto(model);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProducto(@RequestBody ProductoRequestDto requestDto,
                                                 @PathVariable Long id) {
        ProductoModel model = ProductoDtoMapper.toModel(requestDto);
        productoServicePort.updateProducto(model, id);
        return ResponseEntity.ok("Producto actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        productoServicePort.deleteProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> getProductoById(@PathVariable Long id) {
        ProductoModel model = productoServicePort.getProductoById(id);
        return ResponseEntity.ok(ProductoDtoMapper.toResponse(model));
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> getAllProductos() {
        return ResponseEntity.ok(ProductoDtoMapper.toResponseList(productoServicePort.getAllProductos()));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoResponseDto>> getByCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(
                ProductoDtoMapper.toResponseList(productoServicePort.getProductosByCategoriaId(categoriaId))
        );
    }
}
