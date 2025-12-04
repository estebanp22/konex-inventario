package com.konex.inventario.infrastructure.rest.handler;

import com.konex.inventario.domain.exception.DomainException;
import com.konex.inventario.domain.exception.MedicamentoNoEncontradoException;
import com.konex.inventario.domain.exception.StockInsuficienteException;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    private HttpServletRequest mockRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/test");
        return request;
    }

    @Test
    void handleMedicamentoNoEncontrado_devuelve404() {
        HttpServletRequest request = mockRequest();

        MedicamentoNoEncontradoException ex =
                new MedicamentoNoEncontradoException("No existe el medicamento");

        ResponseEntity<ErrorResponse> response =
                handler.handleMedicamentoNoEncontrado(ex, request);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("No existe el medicamento", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void handleStockInsuficiente_devuelve400() {
        HttpServletRequest request = mockRequest();

        StockInsuficienteException ex =
                new StockInsuficienteException("Stock insuficiente");

        ResponseEntity<ErrorResponse> response =
                handler.handleStockInsuficiente(ex, request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Stock insuficiente", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void handleDomainException_devuelve400() {
        HttpServletRequest request = mockRequest();

        DomainException ex = new DomainException("Error de negocio");

        ResponseEntity<ErrorResponse> response =
                handler.handleDomainException(ex, request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error de negocio", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void handleValidation_devuelve400_conMensajesDeCampos() {
        HttpServletRequest request = mockRequest();

        BindingResult bindingResult = mock(BindingResult.class);

        FieldError error1 = new FieldError("obj", "campo1", "obligatorio");
        FieldError error2 = new FieldError("obj", "campo2", "inválido");

        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(error1, error2));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response =
                handler.handleValidation(ex, request);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().getMessage().contains("campo1: obligatorio"));
        assertTrue(response.getBody().getMessage().contains("campo2: inválido"));
        assertEquals("/api/test", response.getBody().getPath());
    }

    @Test
    void handleGeneric_devuelve500() {
        HttpServletRequest request = mockRequest();

        Exception ex = new Exception("Algo explotó");

        ResponseEntity<ErrorResponse> response =
                handler.handleGeneric(ex, request);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Algo explotó", response.getBody().getMessage());
        assertEquals("/api/test", response.getBody().getPath());
    }
}
