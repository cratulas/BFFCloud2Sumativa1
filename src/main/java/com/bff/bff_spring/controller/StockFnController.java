package com.bff.bff_spring.controller;

import com.bff.bff_spring.service.StockFnClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bff/stock-fn")
public class StockFnController {

  private final StockFnClient client;
  public StockFnController(StockFnClient client) { this.client = client; }

  @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> create(@RequestBody String rawJson) {
    return client.forwardCreate(rawJson);
  }

  @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
  public ResponseEntity<String> update(@RequestBody String rawJson) {
    return client.forwardUpdate(rawJson);
  }

  @GetMapping(value = "/producto/{productoId}/bodega/{bodegaId}", produces = "application/json")
  public ResponseEntity<String> byProductoBodega(@PathVariable Long productoId, @PathVariable Long bodegaId) {
    return client.readProductoBodega(productoId, bodegaId);
  }

  @GetMapping(value = "/producto/{productoId}", produces = "application/json")
  public ResponseEntity<String> listByProducto(@PathVariable Long productoId) {
    return client.readByProducto(productoId);
  }

  @GetMapping(value = "/bodega/{bodegaId}", produces = "application/json")
  public ResponseEntity<String> listByBodega(@PathVariable Long bodegaId) {
    return client.readByBodega(bodegaId);
  }
}
