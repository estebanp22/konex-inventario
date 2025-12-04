package com.konex.inventario.application.service;

import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.in.VentaUseCase;
import com.konex.inventario.domain.port.out.VentaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VentaService implements VentaUseCase {

    private final VentaRepositoryPort ventaRepository;

    public VentaService(VentaRepositoryPort ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public Page<Venta> listarVentasPorRangoFechas(LocalDate fechaInicio,
                                                  LocalDate fechaFin,
                                                  Pageable pageable) {
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.atTime(23, 59, 59);

        return ventaRepository.findByFechaBetween(desde, hasta, pageable);
    }

}
