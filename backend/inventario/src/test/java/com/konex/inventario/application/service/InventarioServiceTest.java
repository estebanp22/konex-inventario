package com.konex.inventario.application.service;

import com.konex.inventario.application.validator.MedicamentoValidator;
import com.konex.inventario.application.validator.VentaValidator;
import com.konex.inventario.domain.exception.MedicamentoNoEncontradoException;
import com.konex.inventario.domain.exception.StockInsuficienteException;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.out.MedicamentoRepositoryPort;
import com.konex.inventario.domain.port.out.VentaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private MedicamentoRepositoryPort medicamentoRepository;

    @Mock
    private VentaRepositoryPort ventaRepository;

    @Mock
    private MedicamentoValidator medicamentoValidator;

    @Mock
    private VentaValidator ventaValidator;

    @InjectMocks
    private InventarioService inventarioService;

    Medicamento medicamentoBase;

    @BeforeEach
    void setUp() {
        medicamentoBase = new Medicamento();
        medicamentoBase.setId(1L);
        medicamentoBase.setNombre("Paracetamol");
        medicamentoBase.setLaboratorio("Genfar");
        medicamentoBase.setFechaFabricacion(LocalDate.of(2024,1,1));
        medicamentoBase.setFechaVencimiento(LocalDate.of(2026,1,1));
        medicamentoBase.setCantidadStock(100);
        medicamentoBase.setValorUnitario(new BigDecimal("2000"));
    }

    @Test
    void crearMedicamento_guardaCorrectamente() {
        Medicamento nuevo = new Medicamento();
        nuevo.setNombre("Paracetamol");
        nuevo.setLaboratorio("Genfar");
        nuevo.setFechaFabricacion(LocalDate.of(2024,1,1));
        nuevo.setFechaVencimiento(LocalDate.of(2026,1,1));
        nuevo.setCantidadStock(100);
        nuevo.setValorUnitario(new BigDecimal("2000"));

        when(medicamentoRepository.save(nuevo)).thenReturn(medicamentoBase);
        doNothing().when(medicamentoValidator).validateForCreate(nuevo);

        Medicamento result = inventarioService.crearMedicamento(nuevo);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(medicamentoValidator).validateForCreate(nuevo);
        verify(medicamentoRepository).save(nuevo);
    }

    @Test
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MedicamentoNoEncontradoException.class,
                () -> inventarioService.obtenerPorId(99L));
    }

    @Test
    void venderMedicamento_stockSuficiente_actualizaYRegistraVenta() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamentoBase));
        doNothing().when(ventaValidator).validateCantidad(anyInt());
        when(medicamentoRepository.save(any(Medicamento.class))).thenAnswer(inv -> inv.getArgument(0));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(inv -> inv.getArgument(0));

        Medicamento result = inventarioService.venderMedicamento(1L, 10);

        assertEquals(90, result.getCantidadStock());
        verify(ventaValidator).validateCantidad(10);
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    void venderMedicamento_stockInsuficiente_lanzaExcepcion() {
        Medicamento pocoStock = new Medicamento();
        pocoStock.setId(1L);
        pocoStock.setNombre("Paracetamol");
        pocoStock.setCantidadStock(3);
        pocoStock.setValorUnitario(new BigDecimal("2000"));
        pocoStock.setFechaFabricacion(LocalDate.now());
        pocoStock.setFechaVencimiento(LocalDate.now().plusYears(2));
        pocoStock.setLaboratorio("Genfar");

        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(pocoStock));
        doNothing().when(ventaValidator).validateCantidad(anyInt());

        assertThrows(StockInsuficienteException.class,
                () -> inventarioService.venderMedicamento(1L, 10));

        verify(ventaRepository, never()).save(any());
    }

    @Test
    void eliminarMedicamento_conVentas_lanzaExcepcion() {
        when(ventaRepository.existsByMedicamentoId(1L)).thenReturn(true);
        assertThrows(RuntimeException.class, () -> inventarioService.eliminarMedicamento(1L));
    }

    @Test
    void eliminarMedicamento_sinVentas_eliminaCorrectamente() {
        when(ventaRepository.existsByMedicamentoId(1L)).thenReturn(false);
        inventarioService.eliminarMedicamento(1L);
        verify(medicamentoRepository).deleteById(1L);
    }
}
