package com.konex.inventario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "VENTAS")
@Data
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICAMENTO_ID", nullable = false)
    private MedicamentoEntity medicamento;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "VALOR_UNITARIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "VALOR_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

}
