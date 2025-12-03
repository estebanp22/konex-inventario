package com.konex.inventario.domain.exception;

public class DomainException extends RuntimeException {

    private final String errorCode;

    public DomainException(String message) {
        super(message);
        this.errorCode = null;
    }

    public DomainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
