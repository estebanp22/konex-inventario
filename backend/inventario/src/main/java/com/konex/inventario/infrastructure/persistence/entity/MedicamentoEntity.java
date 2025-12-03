package com.konex.inventario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "MEDICAMENTOS")
@Data
public class MedicamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 200)
    private String nombre;

    @Column(name = "LABORATORIO", length = 200)
    private String laboratorio;

    @Column(name = "FECHA_FAB", nullable = false)
    private LocalDate fechaFabricacion;

    @Column(name = "FECHA_VENC", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "CANTIDAD_STOCK", nullable = false)
    private Integer cantidadStock;

    @Column(name = "VALOR_UNITARIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

}
