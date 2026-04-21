package com.software.hexagonal.demo.infrastructure.input.rest;

import com.software.hexagonal.demo.application.dto.request.CategoriaRequestDto;
import com.software.hexagonal.demo.application.dto.response.CategoriaResponseDto;
import com.software.hexagonal.demo.application.mapper.CategoriaDtoMapper;
import com.software.hexagonal.demo.domain.api.ICategoriaServicePort;
import com.software.hexagonal.demo.domain.model.CategoriaModel;
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
@RequestMapping("/categorias")
public class CategoriaRestController {

    private final ICategoriaServicePort categoriaServicePort;

    public CategoriaRestController(ICategoriaServicePort categoriaServicePort) {
        this.categoriaServicePort = categoriaServicePort;
    }

    @PostMapping
    public ResponseEntity<String> saveCategoria(@RequestBody CategoriaRequestDto requestDto) {
        CategoriaModel model = CategoriaDtoMapper.toModel(requestDto);
        categoriaServicePort.saveCategoria(model);
        return ResponseEntity.status(HttpStatus.CREATED).body("Categoria creada correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategoria(@RequestBody CategoriaRequestDto requestDto,
                                                  @PathVariable Long id) {
        CategoriaModel model = CategoriaDtoMapper.toModel(requestDto);
        categoriaServicePort.updateCategoria(model, id);
        return ResponseEntity.ok("Categoria actualizada correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id) {
        categoriaServicePort.deleteCategoria(id);
        return ResponseEntity.ok("Categoria eliminada correctamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> getCategoriaById(@PathVariable Long id) {
        CategoriaModel model = categoriaServicePort.getCategoriaById(id);
        return ResponseEntity.ok(CategoriaDtoMapper.toResponse(model));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> getAllCategorias() {
        return ResponseEntity.ok(CategoriaDtoMapper.toResponseList(categoriaServicePort.getAllCategorias()));
    }
}
