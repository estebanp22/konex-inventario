package com.konex.inventario.application.service;

import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.out.VentaRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepositoryPort ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void listarVentasPorRangoFechas_delegaEnRepositorio() {
        LocalDate inicio = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 1, 31);
        var pageable = PageRequest.of(0, 10);

        Venta v = new Venta();
        v.setId(1L);
        v.setCantidad(5);
        v.setFechaHora(LocalDateTime.now());

        Page<Venta> page = new PageImpl<>(List.of(v));

        when(ventaRepository.findByFechaBetween(any(), any(), eq(pageable)))
                .thenReturn(page);

        Page<Venta> result = ventaService.listarVentasPorRangoFechas(inicio, fin, pageable);

        assertEquals(1, result.getTotalElements());
        verify(ventaRepository).findByFechaBetween(any(), any(), eq(pageable));
    }
}
