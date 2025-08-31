package com.bff.bff_spring.controller;

import com.bff.bff_spring.dto.BodegaDto;
import com.bff.bff_spring.service.BodegaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/bodegas")
public class BodegaBffController {

  private final BodegaClient client;
  public BodegaBffController(BodegaClient client) { this.client = client; }

  @PostMapping
  public ResponseEntity<String> crear(@RequestBody BodegaDto dto) { return client.crear(dto); }

  @GetMapping
  public ResponseEntity<String> listar() { return client.listar(); }

  @GetMapping("/{id}")
  public ResponseEntity<String> get(@PathVariable Long id) { return client.get(id); }

  @PutMapping("/{id}")
  public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody BodegaDto dto) {
    return client.actualizar(id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> eliminar(@PathVariable Long id) { return client.eliminar(id); }
}
