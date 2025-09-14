package com.bff.bff_spring.service;

import com.bff.bff_spring.dto.StockDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StockClient {

  private final GraphQLClient gql;
  public StockClient(GraphQLClient gql) { this.gql = gql; }

  @Value("${graphql.stock.url}") private String endpoint;

  public ResponseEntity<String> asignar(StockDto dto) {
    String q = "mutation AssignStock($in:StockInput!){ assignStock(input:$in){ id productoId bodegaId stock } }";
    Map<String,Object> vars = Map.of("in", Map.of(
        "productoId", dto.getProductoId(),
        "bodegaId", dto.getBodegaId(),
        "stock", dto.getStock()
    ));
    return gql.execute(endpoint, q, vars, "AssignStock");
  }

  public ResponseEntity<String> byProductoBodega(Long productoId, Long bodegaId) {
    String q = "query StockByProductoBodega($p:ID!,$b:ID!){ stockByProductoBodega(productoId:$p,bodegaId:$b){ id productoId bodegaId stock } }";
    return gql.execute(endpoint, q, Map.of("p", productoId, "b", bodegaId), "StockByProductoBodega");
  }

  public ResponseEntity<String> listByBodega(Long bodegaId) {
    String q = "query StockByBodega($b:ID!){ stockByBodega(bodegaId:$b){ id stock producto { id nombre sku } } }";
    return gql.execute(endpoint, q, Map.of("b", bodegaId), "StockByBodega");
  }

  public ResponseEntity<String> listByProducto(Long productoId) {
    String q = "query StockByProducto($p:ID!){ stockByProducto(productoId:$p){ id stock bodega { id nombre direccion } } }";
    return gql.execute(endpoint, q, Map.of("p", productoId), "StockByProducto");
  }

  public ResponseEntity<String> delete(Long id) {
    return ResponseEntity.status(405).body("{\"error\":\"No soportado en GraphQL\"}");
  }

  public ResponseEntity<String> ejecutarLibre(String query, Map<String,Object> vars, String opName) {
    return gql.execute(endpoint, query, vars, opName);
  }

}
