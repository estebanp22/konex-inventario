import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
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

import { VentaDialogComponent } from '../venta-dialog/venta-dialog.component';

@Component({
  selector: 'app-medicamento-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    FormsModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    PaginatorModule,
    ConfirmDialogModule,
    VentaDialogComponent
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
    private confirmationService: ConfirmationService
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
          console.error('Error cargando medicamentos', err);
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
            next: () => this.cargarMedicamentos(),
            error: err => console.error('Error eliminando', err)
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
