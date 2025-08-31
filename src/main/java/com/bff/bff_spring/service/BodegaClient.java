package com.bff.bff_spring.service;

import com.bff.bff_spring.dto.BodegaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BodegaClient {

  private final RestTemplate rest;
  public BodegaClient(RestTemplate rest) { this.rest = rest; }

  @Value("${ms.bodega.base-url}") private String bodegaBase;

  public ResponseEntity<String> crear(BodegaDto dto) {
    String url = bodegaBase + "/api/bodegas";
    HttpHeaders h = new HttpHeaders(); h.setContentType(MediaType.APPLICATION_JSON);
    return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(dto, h), String.class);
  }

  public ResponseEntity<String> listar() {
    return rest.getForEntity(bodegaBase + "/api/bodegas", String.class);
  }

  public ResponseEntity<String> get(Long id) {
    return rest.getForEntity(bodegaBase + "/api/bodegas/" + id, String.class);
  }

  public ResponseEntity<String> actualizar(Long id, BodegaDto dto) {
    String url = bodegaBase + "/api/bodegas/" + id;
    HttpHeaders h = new HttpHeaders(); h.setContentType(MediaType.APPLICATION_JSON);
    return rest.exchange(url, HttpMethod.PUT, new HttpEntity<>(dto, h), String.class);
  }

  public ResponseEntity<String> eliminar(Long id) {
    return rest.exchange(bodegaBase + "/api/bodegas/" + id, HttpMethod.DELETE, null, String.class);
  }
}
