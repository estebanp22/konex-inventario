package com.konex.inventario.application.mapper;

import com.konex.inventario.application.dto.VentaResponse;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.domain.model.Venta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VentaMapperTest {

    private final VentaMapper mapper = new VentaMapper();

    @Test
    void toResponse_mapeaCorrectamente() {
        Medicamento med = new Medicamento();
        med.setId(1L);
        med.setNombre("Paracetamol");

        Venta v = new Venta();
        v.setId(10L);
        v.setMedicamento(med);
        v.setCantidad(5);
        v.setFechaHora(LocalDateTime.of(2024,1,1,10,0));
        v.setValorUnitario(new BigDecimal("2000"));
        v.setValorTotal(new BigDecimal("10000"));

        VentaResponse resp = mapper.toResponse(v);

        assertEquals(10L, resp.getId());
        assertEquals(1L, resp.getMedicamentoId());
        assertEquals("Paracetamol", resp.getMedicamentoNombre());
        assertEquals(5, resp.getCantidad());
        assertEquals(new BigDecimal("2000"), resp.getValorUnitario());
        assertEquals(new BigDecimal("10000"), resp.getValorTotal());
    }
}
