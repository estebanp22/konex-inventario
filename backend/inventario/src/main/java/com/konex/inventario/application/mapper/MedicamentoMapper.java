package com.konex.inventario.application.mapper;

import com.konex.inventario.application.dto.MedicamentoRequest;
import com.konex.inventario.application.dto.MedicamentoResponse;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.infrastructure.persistence.entity.MedicamentoEntity;

import org.springframework.stereotype.Component;

@Component
public class MedicamentoMapper {

    // ===== DTO -> Domain =====
    public Medicamento toDomain(MedicamentoRequest request) {
        if (request == null) return null;

        Medicamento medicamento = new Medicamento();
        medicamento.setNombre(request.getNombre());
        medicamento.setLaboratorio(request.getLaboratorio());
        medicamento.setFechaFabricacion(request.getFechaFabricacion());
        medicamento.setFechaVencimiento(request.getFechaVencimiento());
        medicamento.setCantidadStock(request.getCantidadStock());
        medicamento.setValorUnitario(request.getValorUnitario());
        return medicamento;
    }

    // ===== Domain -> DTO =====
    public MedicamentoResponse toResponse(Medicamento medicamento) {
        if (medicamento == null) return null;

        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(medicamento.getId());
        response.setNombre(medicamento.getNombre());
        response.setLaboratorio(medicamento.getLaboratorio());
        response.setFechaFabricacion(medicamento.getFechaFabricacion());
        response.setFechaVencimiento(medicamento.getFechaVencimiento());
        response.setCantidadStock(medicamento.getCantidadStock());
        response.setValorUnitario(medicamento.getValorUnitario());
        return response;
    }

    // ===== Entity -> Domain =====
    public Medicamento toDomain(MedicamentoEntity entity) {
        if (entity == null) return null;

        Medicamento medicamento = new Medicamento();
        medicamento.setId(entity.getId());
        medicamento.setNombre(entity.getNombre());
        medicamento.setLaboratorio(entity.getLaboratorio());
        medicamento.setFechaFabricacion(entity.getFechaFabricacion());
        medicamento.setFechaVencimiento(entity.getFechaVencimiento());
        medicamento.setCantidadStock(entity.getCantidadStock());
        medicamento.setValorUnitario(entity.getValorUnitario());
        return medicamento;
    }

    // ===== Domain -> Entity =====
    public MedicamentoEntity toEntity(Medicamento medicamento) {
        if (medicamento == null) return null;

        MedicamentoEntity entity = new MedicamentoEntity();
        entity.setId(medicamento.getId());
        entity.setNombre(medicamento.getNombre());
        entity.setLaboratorio(medicamento.getLaboratorio());
        entity.setFechaFabricacion(medicamento.getFechaFabricacion());
        entity.setFechaVencimiento(medicamento.getFechaVencimiento());
        entity.setCantidadStock(medicamento.getCantidadStock());
        entity.setValorUnitario(medicamento.getValorUnitario());
        return entity;
    }
}
