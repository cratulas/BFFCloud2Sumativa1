package com.bff.bff_spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockFnClient {

  private final RestTemplate rt;
  public StockFnClient(RestTemplate rt) { this.rt = rt; }

  @Value("${fn.stock.create.url}") private String fnCreateUrl;
  @Value("${fn.stock.update.url}") private String fnUpdateUrl;
  @Value("${fn.stock.read.url}")   private String fnReadUrl;

  public ResponseEntity<String> forwardCreate(String rawJson) {
    HttpHeaders h = new HttpHeaders();
    h.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> req = new HttpEntity<>(rawJson, h);
    return rt.exchange(fnCreateUrl, HttpMethod.POST, req, String.class);
  }

  public ResponseEntity<String> forwardUpdate(String rawJson) {
    HttpHeaders h = new HttpHeaders();
    h.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> req = new HttpEntity<>(rawJson, h);
    return rt.exchange(fnUpdateUrl, HttpMethod.POST, req, String.class);
  }

  public ResponseEntity<String> readProductoBodega(Long productoId, Long bodegaId) {
    String url = fnReadUrl + "?productoId={p}&bodegaId={b}";
    return rt.getForEntity(url, String.class, productoId, bodegaId);
  }

  public ResponseEntity<String> readByProducto(Long productoId) {
    String url = fnReadUrl + "?productoId={p}";
    return rt.getForEntity(url, String.class, productoId);
  }

  public ResponseEntity<String> readByBodega(Long bodegaId) {
    String url = fnReadUrl + "?bodegaId={b}";
    return rt.getForEntity(url, String.class, bodegaId);
  }
}
