package com.bff.bff_spring.controller;

import com.bff.bff_spring.dto.StockDto;
import com.bff.bff_spring.service.StockClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/stock")
public class StockBffController {

  private final StockClient client;
  public StockBffController(StockClient client) { this.client = client; }

  @PostMapping("/asignar")
  public ResponseEntity<String> asignar(@RequestBody StockDto dto) { return client.asignar(dto); }

  @GetMapping("/producto/{productoId}/bodega/{bodegaId}")
  public ResponseEntity<String> byProductoBodega(@PathVariable Long productoId, @PathVariable Long bodegaId) {
    return client.byProductoBodega(productoId, bodegaId);
  }

  @GetMapping("/bodega/{bodegaId}")
  public ResponseEntity<String> listByBodega(@PathVariable Long bodegaId) {
    return client.listByBodega(bodegaId);
  }

  @GetMapping("/producto/{productoId}")
  public ResponseEntity<String> listByProducto(@PathVariable Long productoId) {
    return client.listByProducto(productoId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) { return client.delete(id); }
}
