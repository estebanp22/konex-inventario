package com.konex.inventario.application.mapper;

import com.konex.inventario.application.dto.VentaResponse;
import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.infrastructure.persistence.entity.VentaEntity;

import org.springframework.stereotype.Component;

@Component
public class VentaMapper {

    // ===== Entity -> Domain =====
    public Venta toDomain(VentaEntity entity) {
        if (entity == null) return null;

        Venta venta = new Venta();
        venta.setId(entity.getId());
        venta.setFechaHora(entity.getFechaHora());
        venta.setCantidad(entity.getCantidad());
        venta.setValorUnitario(entity.getValorUnitario());
        venta.setValorTotal(entity.getValorTotal());

        if (entity.getMedicamento() != null) {
            var med = new com.konex.inventario.domain.model.Medicamento();
            med.setId(entity.getMedicamento().getId());
            med.setNombre(entity.getMedicamento().getNombre());
            med.setLaboratorio(entity.getMedicamento().getLaboratorio());
            med.setFechaFabricacion(entity.getMedicamento().getFechaFabricacion());
            med.setFechaVencimiento(entity.getMedicamento().getFechaVencimiento());
            med.setCantidadStock(entity.getMedicamento().getCantidadStock());
            med.setValorUnitario(entity.getMedicamento().getValorUnitario());
            venta.setMedicamento(med);
        }

        return venta;
    }

    // ===== Domain -> Entity =====
    public VentaEntity toEntity(Venta venta) {
        if (venta == null) return null;

        VentaEntity entity = new VentaEntity();
        entity.setId(venta.getId());
        entity.setFechaHora(venta.getFechaHora());
        entity.setCantidad(venta.getCantidad());
        entity.setValorUnitario(venta.getValorUnitario());
        entity.setValorTotal(venta.getValorTotal());

        if (venta.getMedicamento() != null) {
            var medEntity = new com.konex.inventario.infrastructure.persistence.entity.MedicamentoEntity();
            medEntity.setId(venta.getMedicamento().getId());
            entity.setMedicamento(medEntity);
        }

        return entity;
    }

    // ===== Domain -> DTO =====
    public VentaResponse toResponse(Venta venta) {
        if (venta == null) return null;

        VentaResponse dto = new VentaResponse();
        dto.setId(venta.getId());
        dto.setFechaHora(venta.getFechaHora());
        if (venta.getMedicamento() != null) {
            dto.setMedicamentoId(venta.getMedicamento().getId());
            dto.setMedicamentoNombre(venta.getMedicamento().getNombre());
        }
        dto.setCantidad(venta.getCantidad());
        dto.setValorUnitario(venta.getValorUnitario());
        dto.setValorTotal(venta.getValorTotal());
        return dto;
    }
}
