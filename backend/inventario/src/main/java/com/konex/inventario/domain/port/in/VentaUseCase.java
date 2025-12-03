package com.konex.inventario.domain.port.in;

import com.konex.inventario.domain.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface VentaUseCase {

    Page<Venta> listarVentasPorRangoFechas(LocalDateTime fechaInicio,
                                           LocalDateTime fechaFin,
                                           Pageable pageable);
}
