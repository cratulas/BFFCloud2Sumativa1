package com.bff.bff_spring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoCreateDto {
  @NotBlank private String nombre;
  @NotBlank private String sku;
  @NotNull @DecimalMin("0.0") private Double precio;
}
