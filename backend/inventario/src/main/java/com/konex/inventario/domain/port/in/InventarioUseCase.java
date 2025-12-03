package com.konex.inventario.domain.port.in;

import com.konex.inventario.domain.model.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventarioUseCase {

    Medicamento crearMedicamento(Medicamento medicamento);

    Medicamento actualizarMedicamento(Long id, Medicamento medicamento);

    void eliminarMedicamento(Long id);

    Medicamento obtenerPorId(Long id);

    Page<Medicamento> listarMedicamentos(String filtroNombre, Pageable pageable);

    Medicamento venderMedicamento(Long idMedicamento, int cantidad);
}
