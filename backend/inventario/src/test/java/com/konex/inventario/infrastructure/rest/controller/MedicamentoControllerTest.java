package com.konex.inventario.infrastructure.rest.controller;

import com.konex.inventario.application.dto.MedicamentoRequest;
import com.konex.inventario.application.dto.MedicamentoResponse;
import com.konex.inventario.application.dto.VentaRequest;
import com.konex.inventario.application.mapper.MedicamentoMapper;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.domain.port.in.InventarioUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicamentoControllerTest {

    @Test
    void crear_devuelve201() {
        InventarioUseCase useCase = mock(InventarioUseCase.class);
        MedicamentoMapper mapper = mock(MedicamentoMapper.class);

        MedicamentoController controller = new MedicamentoController(useCase, mapper);

        MedicamentoRequest request = new MedicamentoRequest();
        Medicamento domain = new Medicamento();
        Medicamento creado = new Medicamento();
        MedicamentoResponse responseMapped = new MedicamentoResponse();

        when(mapper.toDomain(request)).thenReturn(domain);
        when(useCase.crearMedicamento(domain)).thenReturn(creado);
        when(mapper.toResponse(creado)).thenReturn(responseMapped);

        var response = controller.crear(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseMapped, response.getBody());

        verify(mapper).toDomain(request);
        verify(useCase).crearMedicamento(domain);
    }

    @Test
    void obtenerPorId_devuelve200() {
        InventarioUseCase useCase = mock(InventarioUseCase.class);
        MedicamentoMapper mapper = mock(MedicamentoMapper.class);

        MedicamentoController controller = new MedicamentoController(useCase, mapper);

        Medicamento med = new Medicamento();
        MedicamentoResponse mapped = new MedicamentoResponse();

        when(useCase.obtenerPorId(1L)).thenReturn(med);
        when(mapper.toResponse(med)).thenReturn(mapped);

        var response = controller.obtenerPorId(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mapped, response.getBody());

        verify(useCase).obtenerPorId(1L);
    }

    @Test
    void listar_devuelvePage() {
        InventarioUseCase useCase = mock(InventarioUseCase.class);
        MedicamentoMapper mapper = mock(MedicamentoMapper.class);

        MedicamentoController controller = new MedicamentoController(useCase, mapper);

        Pageable pageable = PageRequest.of(0, 10);

        Medicamento med = new Medicamento();
        Page<Medicamento> page = new PageImpl<>(java.util.List.of(med));

        MedicamentoResponse mapped = new MedicamentoResponse();

        when(useCase.listarMedicamentos(null, pageable)).thenReturn(page);
        when(mapper.toResponse(med)).thenReturn(mapped);

        var response = controller.listar(null, 0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(mapped, response.getBody().getContent().get(0));
    }

    @Test
    void actualizar_devuelve200() {
        InventarioUseCase useCase = mock(InventarioUseCase.class);
        MedicamentoMapper mapper = mock(MedicamentoMapper.class);

        MedicamentoController controller = new MedicamentoController(useCase, mapper);

        MedicamentoRequest request = new MedicamentoRequest();
        Medicamento domain = new Medicamento();
        Medicamento actualizado = new Medicamento();
        MedicamentoResponse mapped = new MedicamentoResponse();

        when(mapper.toDomain(request)).thenReturn(domain);
        when(useCase.actualizarMedicamento(2L, domain)).thenReturn(actualizado);
        when(mapper.toResponse(actualizado)).thenReturn(mapped);

        var response = controller.actualizar(2L, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mapped, response.getBody());
    }

    @Test
    void eliminar_devuelve204() {
        InventarioUseCase useCase = mock(InventarioUseCase.class);
        MedicamentoMapper mapper = mock(MedicamentoMapper.class);

        MedicamentoController controller = new MedicamentoController(useCase, mapper);

        var response = controller.eliminar(3L);

        assertEquals(204, response.getStatusCode().value());
        verify(useCase).eliminarMedicamento(3L);
    }

    @Test
    void vender_devuelve200() {
        InventarioUseCase useCase = mock(InventarioUseCase.class);
        MedicamentoMapper mapper = mock(MedicamentoMapper.class);

        MedicamentoController controller = new MedicamentoController(useCase, mapper);

        VentaRequest request = new VentaRequest();
        request.setCantidad(5);

        Medicamento actualizado = new Medicamento();
        MedicamentoResponse mapped = new MedicamentoResponse();

        when(useCase.venderMedicamento(4L, 5)).thenReturn(actualizado);
        when(mapper.toResponse(actualizado)).thenReturn(mapped);

        var response = controller.vender(4L, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mapped, response.getBody());

        verify(useCase).venderMedicamento(4L, 5);
    }
}
