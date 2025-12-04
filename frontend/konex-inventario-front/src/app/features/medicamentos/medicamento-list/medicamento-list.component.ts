import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { Medicamento } from '../../../models/medicamento.model';
import { MedicamentoService } from '../../../services/medicamento.service';

// PrimeNG
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { PaginatorModule } from 'primeng/paginator';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';


import { VentaDialogComponent } from '../venta-dialog/venta-dialog.component';

@Component({
  selector: 'app-medicamento-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    PaginatorModule,
    ConfirmDialogModule,
    VentaDialogComponent,
    ToastModule
  ],
  providers: [ConfirmationService],
  templateUrl: './medicamento-list.component.html',
  styleUrl: './medicamento-list.component.css'
})
export class MedicamentoListComponent implements OnInit {
  medicamentos: Medicamento[] = [];
  filtroNombre = '';
  page = 0;
  pageSize = 10;
  totalElements = 0;
  loading = false;

  ventaDialogVisible = false;
  medicamentoSeleccionado: Medicamento | null = null;

  constructor(
    private medicamentoService: MedicamentoService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private messageService: MessageService

  ) {}

  ngOnInit(): void {
    this.cargarMedicamentos();
  }

  cargarMedicamentos() {
    this.loading = true;
    this.medicamentoService.getMedicamentos(this.filtroNombre, this.page, this.pageSize)
      .subscribe({
        next: resp => {
          this.medicamentos = resp.content;
          this.totalElements = resp.totalElements;
          this.loading = false;
        },
        error: err => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error al cargar medicamentos',
            detail: err?.error?.message || 'Ocurrió un error inesperado',
            life: 4000
          });
          this.loading = false;
        }

      });
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.pageSize = event.rows;
    this.cargarMedicamentos();
  }

  nuevo() {
    this.router.navigate(['/medicamentos/nuevo']);
  }

  editar(med: Medicamento) {
    if (!med.id) return;
    this.router.navigate(['/medicamentos', med.id, 'editar']);
  }

  confirmarEliminar(med: Medicamento) {
    if (!med.id) return;

    this.confirmationService.confirm({
      message: `¿Eliminar el medicamento "${med.nombre}"?`,
      header: 'Confirmar eliminación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.medicamentoService.deleteMedicamento(med.id!)
          .subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Eliminado',
                detail: 'El medicamento fue eliminado',
                life: 2500
              });
              this.cargarMedicamentos();
            },
            error: err => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error eliminando',
                detail: err?.error?.message || 'No se pudo eliminar.',
                life: 4000
              });
            }
          });
      }
    });
  }

  abrirVenta(med: Medicamento) {
    this.medicamentoSeleccionado = med;
    this.ventaDialogVisible = true;
  }

  onVentaRealizada(_: Medicamento) {
    this.cargarMedicamentos();
  }
}
