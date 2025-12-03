package com.konex.inventario.domain.exception;

public class CantidadInvalidaException extends DomainException {

  private static final String DEFAULT_MESSAGE = "La cantidad indicada no es válida";
  private static final String ERROR_CODE = "CANTIDAD_INVALIDA";

  public CantidadInvalidaException() {
    super(DEFAULT_MESSAGE, ERROR_CODE);
  }

  public CantidadInvalidaException(int cantidad) {
    super(DEFAULT_MESSAGE + ": " + cantidad, ERROR_CODE);
  }
}
