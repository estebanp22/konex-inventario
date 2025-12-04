package com.konex.inventario.application.validator;

import com.konex.inventario.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VentaValidatorTest {

    private final VentaValidator validator = new VentaValidator();

    @Test
    void validateCantidad_valorValido_noLanzaExcepcion() {
        assertDoesNotThrow(() -> validator.validateCantidad(5));
    }

    @Test
    void validateCantidad_ceroOLess_lanzaDomainException() {
        assertThrows(DomainException.class, () -> validator.validateCantidad(0));
        assertThrows(DomainException.class, () -> validator.validateCantidad(-3));
    }
}
