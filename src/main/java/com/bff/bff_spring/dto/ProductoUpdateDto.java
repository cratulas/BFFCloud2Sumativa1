package com.bff.bff_spring.dto;

import lombok.Data;

@Data
public class ProductoUpdateDto {
  private String nombre;
  private Double precio;
  private String activo; // "S" o "N"
}
