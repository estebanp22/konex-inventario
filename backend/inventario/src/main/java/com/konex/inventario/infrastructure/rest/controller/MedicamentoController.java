package com.konex.inventario.infrastructure.rest.controller;

import com.konex.inventario.application.dto.MedicamentoRequest;
import com.konex.inventario.application.dto.MedicamentoResponse;
import com.konex.inventario.application.dto.VentaRequest;
import com.konex.inventario.application.mapper.MedicamentoMapper;
import com.konex.inventario.domain.model.Medicamento;
import com.konex.inventario.domain.port.in.InventarioUseCase;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin(origins = "*") // para que el front en Angular no tenga problema de CORS
public class MedicamentoController {

    private final InventarioUseCase inventarioUseCase;
    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoController(InventarioUseCase inventarioUseCase,
                                 MedicamentoMapper medicamentoMapper) {
        this.inventarioUseCase = inventarioUseCase;
        this.medicamentoMapper = medicamentoMapper;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponse> crear(@Valid @RequestBody MedicamentoRequest request) {
        Medicamento medDomain = medicamentoMapper.toDomain(request);
        Medicamento creado = inventarioUseCase.crearMedicamento(medDomain);
        MedicamentoResponse response = medicamentoMapper.toResponse(creado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> obtenerPorId(@PathVariable Long id) {
        Medicamento med = inventarioUseCase.obtenerPorId(id);
        return ResponseEntity.ok(medicamentoMapper.toResponse(med));
    }

    @GetMapping
    public ResponseEntity<Page<MedicamentoResponse>> listar(
            @RequestParam(value = "filtroNombre", required = false) String filtroNombre,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Medicamento> pageMed = inventarioUseCase.listarMedicamentos(filtroNombre, pageable);
        Page<MedicamentoResponse> responsePage = pageMed.map(medicamentoMapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MedicamentoRequest request) {

        Medicamento medDomain = medicamentoMapper.toDomain(request);
        Medicamento actualizado = inventarioUseCase.actualizarMedicamento(id, medDomain);
        return ResponseEntity.ok(medicamentoMapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioUseCase.eliminarMedicamento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/vender")
    public ResponseEntity<MedicamentoResponse> vender(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequest request) {

        Medicamento actualizado = inventarioUseCase.venderMedicamento(id, request.getCantidad());
        MedicamentoResponse response = medicamentoMapper.toResponse(actualizado);
        return ResponseEntity.ok(response);
    }
}
