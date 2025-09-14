package com.bff.bff_spring.controller;

import com.bff.bff_spring.dto.BodegaDto;
import com.bff.bff_spring.service.BodegaClient;

import java.util.Map;

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

  // ====== GraphQL ====== // 
  @PostMapping("/graphql")
  public ResponseEntity<String> proxyGraphQL(@RequestBody Map<String,Object> payload) {
    Object q = payload.get("query");
    if (q == null || String.valueOf(q).isBlank()) {
      return ResponseEntity.badRequest().body("{\"error\":\"'query' es obligatoria\"}");
    }
    @SuppressWarnings("unchecked")
    Map<String,Object> vars = (Map<String,Object>) payload.getOrDefault("variables", Map.of());
    String opName = payload.get("operationName") == null ? null : String.valueOf(payload.get("operationName"));
    return client.ejecutarLibre(String.valueOf(q), vars, opName);
  }
}
