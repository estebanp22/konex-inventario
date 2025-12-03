package com.konex.inventario.application.validator;

import com.konex.inventario.domain.exception.DomainException;
import com.konex.inventario.domain.model.Medicamento;
import org.springframework.stereotype.Component;

@Component
public class MedicamentoValidator {

    public void validateForCreate(Medicamento medicamento) {
        validateCommon(medicamento);
    }

    public void validateForUpdate(Medicamento medicamento) {
        validateCommon(medicamento);
    }

    private void validateCommon(Medicamento medicamento) {
        if (medicamento.getFechaFabricacion().isAfter(medicamento.getFechaVencimiento())) {
            throw new DomainException("La fecha de fabricación no puede ser posterior a la fecha de vencimiento.");
        }

        if (medicamento.getCantidadStock() != null && medicamento.getCantidadStock() < 0) {
            throw new DomainException("La cantidad en stock no puede ser negativa.");
        }
    }
}
