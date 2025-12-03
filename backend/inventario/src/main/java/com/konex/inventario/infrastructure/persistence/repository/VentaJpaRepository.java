package com.konex.inventario.infrastructure.persistence.repository;

import com.konex.inventario.infrastructure.persistence.entity.VentaEntity;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaJpaRepository extends JpaRepository<VentaEntity, Long> {

    Page<VentaEntity> findByFechaHoraBetween(LocalDateTime desde,
                                             LocalDateTime hasta,
                                             Pageable pageable);
}
