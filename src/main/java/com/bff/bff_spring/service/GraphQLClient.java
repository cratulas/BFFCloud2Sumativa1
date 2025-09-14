package com.bff.bff_spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GraphQLClient {
  private final RestTemplate rest;
  private final ObjectMapper mapper = new ObjectMapper();

  public GraphQLClient(RestTemplate rest) { this.rest = rest; }

  public ResponseEntity<String> execute(String endpoint, String query, Map<String,Object> variables, String opName) {
    try {
      if (endpoint == null || endpoint.isBlank()) {
        return ResponseEntity.internalServerError().body("{\"error\":\"graphql endpoint vacío\"}");
      }
      if (query == null || query.isBlank()) {
        return ResponseEntity.badRequest().body("{\"error\":\"query vacía\"}");
      }

      HttpHeaders h = new HttpHeaders();
      h.setContentType(MediaType.APPLICATION_JSON);
      h.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

      Map<String,Object> payload = new HashMap<>();
      payload.put("query", query);
      payload.put("variables", variables == null ? Map.of() : variables);
      if (opName != null && !opName.isBlank()) {
        payload.put("operationName", opName);
      }

      String body = mapper.writeValueAsString(payload);
      HttpEntity<String> entity = new HttpEntity<>(body, h);

      return rest.exchange(endpoint, HttpMethod.POST, entity, String.class);

    } catch (HttpStatusCodeException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("{\"error\":\"" + String.valueOf(e) + "\"}");
    }
  }
}
