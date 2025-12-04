package com.konex.inventario.application.service;

import com.konex.inventario.application.validator.MedicamentoValidator;
import com.konex.inventario.application.validator.VentaValidator;
import com.konex.inventario.domain.exception.DomainException;
import com.konex.inventario.domain.exception.MedicamentoNoEncontradoException;
import com.konex.inventario.domain.exception.StockInsuficienteException;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.in.InventarioUseCase;
import com.konex.inventario.domain.port.out.MedicamentoRepositoryPort;
import com.konex.inventario.domain.port.out.VentaRepositoryPort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class InventarioService implements InventarioUseCase {

    private final MedicamentoRepositoryPort medicamentoRepository;
    private final VentaRepositoryPort ventaRepository;
    private final MedicamentoValidator medicamentoValidator;
    private final VentaValidator ventaValidator;


    public InventarioService(MedicamentoRepositoryPort medicamentoRepository,
                             VentaRepositoryPort ventaRepository,
                             MedicamentoValidator medicamentoValidator,
                             VentaValidator ventaValidator) {
        this.medicamentoRepository = medicamentoRepository;
        this.ventaRepository = ventaRepository;
        this.medicamentoValidator = medicamentoValidator;
        this.ventaValidator = ventaValidator;
    }

    @Override
    public Medicamento crearMedicamento(Medicamento medicamento) {
        medicamentoValidator.validateForCreate(medicamento);
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento actualizarMedicamento(Long id, Medicamento medicamento) {
        Medicamento existente = medicamentoRepository.findById(id)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(id));

        // Actualiza campos permitidos
        existente.setNombre(medicamento.getNombre());
        existente.setLaboratorio(medicamento.getLaboratorio());
        existente.setFechaFabricacion(medicamento.getFechaFabricacion());
        existente.setFechaVencimiento(medicamento.getFechaVencimiento());
        existente.setCantidadStock(medicamento.getCantidadStock());
        existente.setValorUnitario(medicamento.getValorUnitario());

        medicamentoValidator.validateForUpdate(existente);
        return medicamentoRepository.save(existente);
    }

    @Override
    public void eliminarMedicamento(Long id) {
        if (ventaRepository.existsByMedicamentoId(id)) {
            throw new DomainException("No se puede eliminar el medicamento porque tiene ventas registradas.");
        }

        medicamentoRepository.deleteById(id);
    }

    @Override
    public Medicamento obtenerPorId(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(id));
    }

    @Override
    public Page<Medicamento> listarMedicamentos(String filtroNombre, Pageable pageable) {
        String filtro = (filtroNombre == null) ? "" : filtroNombre;
        return medicamentoRepository.findByNombreContainingIgnoreCase(filtro, pageable);
    }

    @Override
    public Medicamento venderMedicamento(Long idMedicamento, int cantidad) {
        ventaValidator.validateCantidad(cantidad);

        Medicamento med = medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new MedicamentoNoEncontradoException(idMedicamento));

        if (med.getCantidadStock() < cantidad) {
            throw new StockInsuficienteException(idMedicamento);
        }

        // Actualizar stock
        med.setCantidadStock(med.getCantidadStock() - cantidad);

        // Registrar venta
        Venta venta = new Venta();
        venta.setMedicamento(med);
        venta.setCantidad(cantidad);
        venta.setFechaHora(LocalDateTime.now());
        venta.setValorUnitario(med.getValorUnitario());
        venta.setValorTotal(
                med.getValorUnitario().multiply(BigDecimal.valueOf(cantidad))
        );

        ventaRepository.save(venta);
        return medicamentoRepository.save(med);
    }
}
