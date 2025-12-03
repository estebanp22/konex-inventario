package com.konex.inventario.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Venta {

    private Long id;
    private LocalDateTime fechaHora;
    private Medicamento medicamento;
    private Integer cantidad;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

}
