package com.konex.inventario.application.mapper;

import com.konex.inventario.application.dto.MedicamentoRequest;
import com.konex.inventario.application.dto.MedicamentoResponse;
import com.konex.inventario.domain.model.Medicamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoMapperTest {

    private final MedicamentoMapper mapper = new MedicamentoMapper();

    @Test
    void toDomain_mapeaCorrectamente() {
        MedicamentoRequest req = new MedicamentoRequest();
        req.setNombre("Paracetamol");
        req.setLaboratorio("Genfar");
        req.setFechaFabricacion(LocalDate.of(2024,1,1));
        req.setFechaVencimiento(LocalDate.of(2026,1,1));
        req.setCantidadStock(100);
        req.setValorUnitario(new BigDecimal("2000"));

        Medicamento med = mapper.toDomain(req);

        assertEquals("Paracetamol", med.getNombre());
        assertEquals("Genfar", med.getLaboratorio());
        assertEquals(100, med.getCantidadStock());
        assertEquals(new BigDecimal("2000"), med.getValorUnitario());
    }

    @Test
    void toResponse_mapeaCorrectamente() {
        Medicamento med = new Medicamento();
        med.setId(1L);
        med.setNombre("Paracetamol");
        med.setLaboratorio("Genfar");
        med.setCantidadStock(100);

        MedicamentoResponse resp = mapper.toResponse(med);

        assertEquals(1L, resp.getId());
        assertEquals("Paracetamol", resp.getNombre());
        assertEquals("Genfar", resp.getLaboratorio());
        assertEquals(100, resp.getCantidadStock());
    }
}
