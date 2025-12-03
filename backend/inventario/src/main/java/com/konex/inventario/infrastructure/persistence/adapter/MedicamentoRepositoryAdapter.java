package com.konex.inventario.infrastructure.persistence.adapter;

import com.konex.inventario.application.mapper.MedicamentoMapper;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.domain.port.out.MedicamentoRepositoryPort;
import com.konex.inventario.infrastructure.persistence.entity.MedicamentoEntity;
import com.konex.inventario.infrastructure.persistence.repository.MedicamentoJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MedicamentoRepositoryAdapter implements MedicamentoRepositoryPort {

    private final MedicamentoJpaRepository jpaRepository;
    private final MedicamentoMapper mapper;

    public MedicamentoRepositoryAdapter(MedicamentoJpaRepository jpaRepository,
                                        MedicamentoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Medicamento save(Medicamento medicamento) {
        MedicamentoEntity entity = mapper.toEntity(medicamento);
        MedicamentoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Medicamento> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Page<Medicamento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable) {
        return jpaRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(mapper::toDomain);
    }
}
