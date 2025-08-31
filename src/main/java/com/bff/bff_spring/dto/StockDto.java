package com.bff.bff_spring.dto;

import lombok.Data;

@Data
public class StockDto {
  private Long id;
  private Long productoId;
  private Long bodegaId;
  private Integer stock;
}
