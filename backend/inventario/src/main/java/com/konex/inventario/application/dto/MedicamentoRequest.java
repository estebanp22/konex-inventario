package com.konex.inventario.application.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String laboratorio;

    @NotNull
    private LocalDate fechaFabricacion;

    @NotNull
    private LocalDate fechaVencimiento;

    @NotNull
    @PositiveOrZero
    private Integer cantidadStock;

    @NotNull
    @Positive
    private BigDecimal valorUnitario;
}
