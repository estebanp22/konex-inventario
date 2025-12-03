package com.konex.inventario.domain.exception;

public class MedicamentoNoEncontradoException extends DomainException {

    private static final String DEFAULT_MESSAGE = "Medicamento no encontrado";
    private static final String ERROR_CODE = "MEDICAMENTO_NO_ENCONTRADO";

    public MedicamentoNoEncontradoException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public MedicamentoNoEncontradoException(Long id) {
        super(DEFAULT_MESSAGE + " con ID: " + id, ERROR_CODE);
    }

    public MedicamentoNoEncontradoException(String message) {
        super(message, ERROR_CODE);
    }
}
