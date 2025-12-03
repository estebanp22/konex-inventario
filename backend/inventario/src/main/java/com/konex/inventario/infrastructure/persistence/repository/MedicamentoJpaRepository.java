package com.konex.inventario.infrastructure.persistence.repository;

import com.konex.inventario.infrastructure.persistence.entity.MedicamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoJpaRepository extends JpaRepository<MedicamentoEntity, Long> {

    Page<MedicamentoEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
