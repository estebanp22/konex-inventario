package com.konex.inventario.domain.port.out;

import com.konex.inventario.domain.model.Medicamento;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicamentoRepositoryPort {

    Medicamento save(Medicamento medicamento);

    Optional<Medicamento> findById(Long id);

    void deleteById(Long id);

    Page<Medicamento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

}
