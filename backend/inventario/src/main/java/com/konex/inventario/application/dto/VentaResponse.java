package com.konex.inventario.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponse {

    private Long id;
    private LocalDateTime fechaHora;
    private Long medicamentoId;
    private String medicamentoNombre;
    private Integer cantidad;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
}
