package com.konex.inventario.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Medicamento {

    private Long id;
    private String nombre;
    private String laboratorio;
    private LocalDate fechaFabricacion;
    private LocalDate fechaVencimiento;
    private Integer cantidadStock;
    private BigDecimal valorUnitario;
}