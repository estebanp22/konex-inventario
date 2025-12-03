package com.konex.inventario.application.service;

import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.in.VentaUseCase;
import com.konex.inventario.domain.port.out.VentaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VentaService implements VentaUseCase {

    private final VentaRepositoryPort ventaRepository;

    public VentaService(VentaRepositoryPort ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public Page<Venta> listarVentasPorRangoFechas(LocalDateTime fechaInicio,
                                                  LocalDateTime fechaFin,
                                                  Pageable pageable) {
        return ventaRepository.findByFechaBetween(fechaInicio, fechaFin, pageable);
    }
}
