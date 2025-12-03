package com.konex.inventario.infrastructure.rest.controller;

import com.konex.inventario.application.dto.VentaResponse;
import com.konex.inventario.application.mapper.VentaMapper;
import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.in.VentaUseCase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaUseCase ventaUseCase;
    private final VentaMapper ventaMapper;

    public VentaController(VentaUseCase ventaUseCase, VentaMapper ventaMapper) {
        this.ventaUseCase = ventaUseCase;
        this.ventaMapper = ventaMapper;
    }

    @GetMapping
    public ResponseEntity<Page<VentaResponse>> listarPorRangoFechas(
            @RequestParam("fechaInicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaInicio,
            @RequestParam("fechaFin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaFin,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Venta> pageVentas = ventaUseCase.listarVentasPorRangoFechas(fechaInicio, fechaFin, pageable);
        Page<VentaResponse> responsePage = pageVentas.map(ventaMapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }
}
