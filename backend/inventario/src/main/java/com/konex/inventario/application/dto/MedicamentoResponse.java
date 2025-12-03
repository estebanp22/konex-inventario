package com.konex.inventario.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponse {

    private Long id;
    private String nombre;
    private String laboratorio;
    private LocalDate fechaFabricacion;
    private LocalDate fechaVencimiento;
    private Integer cantidadStock;
    private BigDecimal valorUnitario;
}
