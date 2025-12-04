package com.konex.inventario.infrastructure.persistence.adapter;

import com.konex.inventario.application.mapper.VentaMapper;
import com.konex.inventario.domain.model.Venta;
import com.konex.inventario.domain.port.out.VentaRepositoryPort;
import com.konex.inventario.infrastructure.persistence.entity.VentaEntity;
import com.konex.inventario.infrastructure.persistence.repository.VentaJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VentaRepositoryAdapter implements VentaRepositoryPort {

    private final VentaJpaRepository jpaRepository;
    private final VentaMapper mapper;

    public VentaRepositoryAdapter(VentaJpaRepository jpaRepository, VentaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Venta save(Venta venta) {
        VentaEntity entity = mapper.toEntity(venta);
        VentaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Page<Venta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta, Pageable pageable) {
        return jpaRepository.findByFechaHoraBetween(desde, hasta, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByMedicamentoId(Long medicamentoId) {
        return jpaRepository.existsByMedicamento_Id(medicamentoId);
    }
}
