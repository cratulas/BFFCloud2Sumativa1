package com.bff.bff_spring.controller;

import com.bff.bff_spring.dto.ProductoCreateDto;
import com.bff.bff_spring.dto.ProductoUpdateDto;
import com.bff.bff_spring.service.ProductFunctionsClient;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/productos")
public class ProductoBffController {

  private final ProductFunctionsClient client;
  public ProductoBffController(ProductFunctionsClient client) { this.client = client; }

  @PostMapping
  public ResponseEntity<String> crear(@Valid @RequestBody ProductoCreateDto dto) {
    return client.crear(dto);
  }

  @GetMapping
  public ResponseEntity<String> listar() { return client.listar(); }

  @GetMapping("/{id}")
  public ResponseEntity<String> obtener(@PathVariable Long id) { return client.obtener(id); }

  @PutMapping("/{id}")
  public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody ProductoUpdateDto dto) {
    return client.actualizar(id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> eliminar(@PathVariable Long id,
                                         @RequestParam(defaultValue = "false") boolean hard) {
    return client.eliminar(id, hard);
  }
}
