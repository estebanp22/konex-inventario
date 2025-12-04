import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Medicamento } from '../../../models/medicamento.model';
import { MedicamentoService } from '../../../services/medicamento.service';

import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';


@Component({
  selector: 'app-venta-dialog',
  standalone: true,
  imports: [CommonModule, DialogModule, InputNumberModule, ButtonModule, FormsModule, ToastModule],
  templateUrl: './venta-dialog.component.html',
  styleUrl: './venta-dialog.component.css'
})
export class VentaDialogComponent {
  @Input() visible = false;
  @Input() medicamento: Medicamento | null = null;

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() ventaRealizada = new EventEmitter<Medicamento>();

  cantidad = 1;

  constructor(
      private medicamentoService: MedicamentoService,
      private messageService: MessageService
  ) {}

  get valorTotal(): number {
    if (!this.medicamento || !this.cantidad) return 0;
    return this.medicamento.valorUnitario * this.cantidad;
  }

  cerrar() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

  confirmar() {
    if (!this.medicamento || !this.medicamento.id) return;
    this.medicamentoService.venderMedicamento(this.medicamento.id, this.cantidad)
      .subscribe({
        next: (medActualizado) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Venta realizada',
            detail: 'El inventario fue actualizado.',
            life: 3000
          });
          this.ventaRealizada.emit(medActualizado);
          this.cerrar();
        },
        error: err => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error en la venta',
            detail: err?.error?.message || 'No se pudo realizar la venta.',
            life: 4000
          });
        }
      });
  }
}
