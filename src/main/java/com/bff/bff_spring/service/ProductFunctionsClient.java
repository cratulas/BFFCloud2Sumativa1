package com.bff.bff_spring.service;

import com.bff.bff_spring.dto.ProductoCreateDto;
import com.bff.bff_spring.dto.ProductoUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductFunctionsClient {

  private final RestTemplate rest;
  private final ObjectMapper mapper = new ObjectMapper();

  public ProductFunctionsClient(RestTemplate rest) { this.rest = rest; }

  @Value("${fn.product.create.url}") private String createUrl;
  @Value("${fn.product.readAll.url}") private String readAllUrl;
  @Value("${fn.product.readById.url}") private String readByIdUrl;
  @Value("${fn.product.update.url}") private String updateUrl;
  @Value("${fn.product.delete.url}") private String deleteUrl;

  public ResponseEntity<String> crear(ProductoCreateDto dto) {
    try {
      String json = mapper.writeValueAsString(dto);           // 👈 serializamos a String
      HttpHeaders h = new HttpHeaders();
      h.setContentType(MediaType.APPLICATION_JSON);
      h.setAccept(List.of(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<>(json, h);
      return rest.exchange(createUrl, HttpMethod.POST, entity, String.class);
    } catch (HttpStatusCodeException e) {
      // Propaga mismo status y body que retornó la Function
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
    }
  }

  public ResponseEntity<String> listar() {
    try {
      return rest.getForEntity(readAllUrl, String.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  public ResponseEntity<String> obtener(Long id) {
    String url = readByIdUrl.replace("{id}", String.valueOf(id));
    try {
      return rest.getForEntity(url, String.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }

  public ResponseEntity<String> actualizar(Long id, ProductoUpdateDto dto) {
    try {
      String json = mapper.writeValueAsString(dto);
      HttpHeaders h = new HttpHeaders();
      h.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<>(json, h);
      String url = updateUrl.replace("{id}", String.valueOf(id));
      return rest.exchange(url, HttpMethod.PUT, entity, String.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
    }
  }

  public ResponseEntity<String> eliminar(Long id, boolean hard) {
    String url = deleteUrl.replace("{id}", String.valueOf(id));
    if (hard) url += "?hard=true";
    try {
      return rest.exchange(url, HttpMethod.DELETE, null, String.class);
    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }
  }
}

