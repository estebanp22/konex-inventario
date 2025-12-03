package com.konex.inventario.domain.exception;

public class StockInsuficienteException extends DomainException {

    private static final String DEFAULT_MESSAGE = "Stock insuficiente para realizar la venta";
    private static final String ERROR_CODE = "STOCK_INSUFICIENTE";

    public StockInsuficienteException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }

    public StockInsuficienteException(Long idMedicamento) {
        super(DEFAULT_MESSAGE + " del medicamento con ID: " + idMedicamento, ERROR_CODE);
    }

    public StockInsuficienteException(String message) {
        super(message, ERROR_CODE);
    }
}
