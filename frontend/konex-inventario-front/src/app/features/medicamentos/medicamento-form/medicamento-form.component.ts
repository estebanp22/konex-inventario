import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MedicamentoService } from '../../../services/medicamento.service';
import { Medicamento } from '../../../models/medicamento.model';

// PrimeNG
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-medicamento-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    InputNumberModule,
    DatePickerModule,
    ButtonModule,
    CardModule
  ],
  templateUrl: './medicamento-form.component.html',
  styleUrl: './medicamento-form.component.css'
})
export class MedicamentoFormComponent implements OnInit {

  form!: FormGroup;
  esEdicion = false;
  idMedicamento?: number;

  cargando = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private medicamentoService: MedicamentoService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      laboratorio: ['', Validators.required],
      fechaFabricacion: [null, Validators.required],
      fechaVencimiento: [null, Validators.required],
      cantidadStock: [0, [Validators.required, Validators.min(0)]],
      valorUnitario: [0, [Validators.required, Validators.min(0.01)]]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.esEdicion = true;
        this.idMedicamento = +id;
        this.cargarMedicamento(+id);
      }
    });
  }

  cargarMedicamento(id: number) {
    this.cargando = true;
    this.medicamentoService.getMedicamento(id).subscribe({
      next: (m) => {
        // Convertimos strings ISO a Date para el DatePicker
        this.form.patchValue({
          nombre: m.nombre,
          laboratorio: m.laboratorio,
          fechaFabricacion: new Date(m.fechaFabricacion),
          fechaVencimiento: new Date(m.fechaVencimiento),
          cantidadStock: m.cantidadStock,
          valorUnitario: m.valorUnitario
        });
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error cargando medicamento', err);
        this.cargando = false;
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.value;

    const payload: Medicamento = {
      nombre: raw.nombre,
      laboratorio: raw.laboratorio,
      fechaFabricacion: this.toDateString(raw.fechaFabricacion),
      fechaVencimiento: this.toDateString(raw.fechaVencimiento),
      cantidadStock: raw.cantidadStock,
      valorUnitario: raw.valorUnitario
    };

    this.cargando = true;

    if (this.esEdicion && this.idMedicamento) {
      this.medicamentoService.updateMedicamento(this.idMedicamento, payload)
        .subscribe({
          next: () => {
            this.cargando = false;
            this.messageService.add({
              severity: 'success',
              summary: 'Actualizado',
              detail: 'Medicamento actualizado correctamente.',
              life: 3000
            });
            this.router.navigate(['/medicamentos']);
          },
          error: (err) => {
            console.error('Error actualizando', err);
            this.cargando = false;
            this.messageService.add({
              severity: 'error',
              summary: 'Error al actualizar',
              detail: err?.error?.message || 'No se pudo actualizar el medicamento.',
              life: 4000
            });
          }
        });
    } else {
      this.medicamentoService.createMedicamento(payload)
        .subscribe({
          next: () => {
            this.cargando = false;  // 👈 IMPORTANTE
            this.messageService.add({
              severity: 'success',
              summary: 'Creado',
              detail: 'Medicamento registrado correctamente.',
              life: 3000
            });
            this.router.navigate(['/medicamentos']);
          },
          error: err => {
            this.cargando = false;  // 👈 IMPORTANTE
            this.messageService.add({
              severity: 'error',
              summary: 'Error al crear',
              detail: err?.error?.message || 'No se pudo crear el medicamento.',
              life: 4000
            });
          }
        });
    }
  }

  cancelar() {
    this.router.navigate(['/medicamentos']);
  }

  private toDateString(value: any): string {
    // El DatePicker devuelve Date
    if (value instanceof Date) {
      return value.toISOString().substring(0, 10); // yyyy-MM-dd
    }
    return value;
  }
}
