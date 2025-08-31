package com.bff.bff_spring.dto;

import lombok.Data;

@Data
public class BodegaDto {
  private Long id;
  private String nombre;
  private String direccion;
  private String activo; // "S"|"N"
}
