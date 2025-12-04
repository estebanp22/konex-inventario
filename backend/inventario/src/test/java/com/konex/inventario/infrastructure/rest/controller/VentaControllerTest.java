package com.konex.inventario.infrastructure.rest.controller;

import com.konex.inventario.application.dto.VentaResponse;
import com.konex.inventario.application.mapper.VentaMapper;
import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.in.VentaUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaControllerTest {

    @Test
    void listarPorRangoFechas_devuelvePageOk() {
        VentaUseCase useCase = mock(VentaUseCase.class);
        VentaMapper mapper = mock(VentaMapper.class);

        VentaController controller = new VentaController(useCase, mapper);

        LocalDate inicio = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 1, 31);

        Pageable pageable = PageRequest.of(0, 10);

        Venta venta = new Venta();
        Page<Venta> pageVentas = new PageImpl<>(java.util.List.of(venta));

        when(useCase.listarVentasPorRangoFechas(inicio, fin, pageable))
                .thenReturn(pageVentas);

        VentaResponse responseMapped = new VentaResponse();
        when(mapper.toResponse(venta)).thenReturn(responseMapped);

        var response = controller.listarPorRangoFechas(inicio, fin, 0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(responseMapped, response.getBody().getContent().get(0));

        verify(useCase).listarVentasPorRangoFechas(inicio, fin, pageable);
        verify(mapper).toResponse(venta);
    }
}
