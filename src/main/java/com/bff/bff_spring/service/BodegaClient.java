package com.bff.bff_spring.service;

import com.bff.bff_spring.dto.BodegaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BodegaClient {

  private final GraphQLClient gql;
  public BodegaClient(GraphQLClient gql) { this.gql = gql; }

  @Value("${graphql.bodegas.url}") private String endpoint;

  public ResponseEntity<String> listar() {
    String q = "query ListBodegas { bodegas { id nombre direccion activo } }";
    return gql.execute(endpoint, q, Map.of(), "ListBodegas");
  }

  public ResponseEntity<String> get(Long id) {
    String q = "query GetBodega($id:ID!){ bodega(id:$id){ id nombre direccion activo } }";
    return gql.execute(endpoint, q, Map.of("id", id), "GetBodega");
  }

  public ResponseEntity<String> crear(BodegaDto dto) {
    String q = "mutation CreateBodega($in:BodegaInput!){ createBodega(input:$in){ id nombre direccion activo } }";
    Map<String,Object> vars = Map.of("in", Map.of(
        "nombre", dto.getNombre(),
        "direccion", dto.getDireccion(),
        "activo", dto.getActivo() == null ? "S" : dto.getActivo()
    ));
    return gql.execute(endpoint, q, vars, "CreateBodega");
  }

  public ResponseEntity<String> actualizar(Long id, BodegaDto dto) {
    String q = "mutation UpdateBodega($id:ID!,$in:BodegaUpdateInput!){ updateBodega(id:$id,input:$in){ id nombre direccion activo } }";
    Map<String,Object> in = Map.of(
        "nombre", dto.getNombre(),
        "direccion", dto.getDireccion(),
        "activo", dto.getActivo()
    );
    return gql.execute(endpoint, q, Map.of("id", id, "in", in), "UpdateBodega");
  }

  public ResponseEntity<String> eliminar(Long id) {
    String q = "mutation SoftDeleteBodega($id:ID!){ softDeleteBodega(id:$id) }";
    return gql.execute(endpoint, q, Map.of("id", id), "SoftDeleteBodega");
  }

  public ResponseEntity<String> ejecutarLibre(String query, Map<String,Object> vars, String opName) {
    return gql.execute(endpoint, query, vars, opName);
  }
}
