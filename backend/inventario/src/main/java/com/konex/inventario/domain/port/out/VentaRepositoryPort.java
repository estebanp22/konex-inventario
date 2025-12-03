package com.konex.inventario.domain.port.out;

import com.konex.inventario.domain.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface VentaRepositoryPort {

    Venta save(Venta venta);

    Page<Venta> findByFechaBetween(LocalDateTime desde,
                                   LocalDateTime hasta,
                                   Pageable pageable);
}
