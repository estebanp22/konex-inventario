package com.konex.inventario.application.validator;

import com.konex.inventario.domain.exception.DomainException;
import org.springframework.stereotype.Component;

@Component
public class VentaValidator {

    public void validateCantidad(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new DomainException("La cantidad a vender debe ser mayor que cero.");
        }
    }
}
