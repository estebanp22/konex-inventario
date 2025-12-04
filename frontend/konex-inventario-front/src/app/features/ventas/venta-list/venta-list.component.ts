import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { VentaService } from '../../../services/venta.service';
import { Venta } from '../../../models/venta.model';
import { PageResponse } from '../../../models/page.model';

// PrimeNG
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';
import { PaginatorModule } from 'primeng/paginator';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-venta-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TableModule,
    DatePickerModule,
    ButtonModule,
    PaginatorModule
  ],
  templateUrl: './venta-list.component.html',
  styleUrl: './venta-list.component.css'
})
export class VentaListComponent implements OnInit {

  ventas: Venta[] = [];

  fechaInicio: Date | null = null;
  fechaFin: Date | null = null;

  page = 0;
  pageSize = 10;
  totalElements = 0;
  loading = false;

  constructor(private ventaService: VentaService, private messageService: MessageService
  ) {}

  ngOnInit(): void {
    // Por defecto, rango de hoy
    const hoy = new Date();
    this.fechaInicio = hoy;
    this.fechaFin = hoy;
    this.buscar();
  }

  buscar() {
    if (!this.fechaInicio || !this.fechaFin) return;

    const fi = this.fechaInicio.toISOString().substring(0, 10);
    const ff = this.fechaFin.toISOString().substring(0, 10);

    this.loading = true;
    this.ventaService.getVentas(fi, ff, this.page, this.pageSize)
      .subscribe({
        next: (resp: PageResponse<Venta>) => {
          this.ventas = resp.content;
          this.totalElements = resp.totalElements;
          this.loading = false;
        },
        error: err => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error cargando ventas',
            detail: err?.error?.message || 'No se pudo obtener el listado de ventas.',
            life: 4000
          });
          this.loading = false;
        }

      });
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.pageSize = event.rows;
    this.buscar();
  }
}
