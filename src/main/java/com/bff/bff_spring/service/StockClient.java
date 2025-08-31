package com.bff.bff_spring.service;

import com.bff.bff_spring.dto.StockDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockClient {

  private final RestTemplate rest;
  public StockClient(RestTemplate rest) { this.rest = rest; }

  @Value("${ms.stock.base-url}") private String stockBase;

  public ResponseEntity<String> asignar(StockDto dto) {
    String url = stockBase + "/api/stock/asignar";
    HttpHeaders h = new HttpHeaders(); h.setContentType(MediaType.APPLICATION_JSON);
    return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(dto, h), String.class);
  }

  public ResponseEntity<String> byProductoBodega(Long productoId, Long bodegaId) {
    return rest.getForEntity(
        stockBase + "/api/stock/producto/" + productoId + "/bodega/" + bodegaId, String.class);
  }

  public ResponseEntity<String> listByBodega(Long bodegaId) {
    return rest.getForEntity(stockBase + "/api/stock/bodega/" + bodegaId, String.class);
  }

  public ResponseEntity<String> listByProducto(Long productoId) {
    return rest.getForEntity(stockBase + "/api/stock/producto/" + productoId, String.class);
  }

  public ResponseEntity<String> delete(Long id) {
    return rest.exchange(stockBase + "/api/stock/" + id, HttpMethod.DELETE, null, String.class);
  }
}
