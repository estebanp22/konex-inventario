import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Medicamento } from '../../../models/medicamento.model';
import { MedicamentoService } from '../../../services/medicamento.service';

import { DialogModule } from 'primeng/dialog';
import { InputNumberModule } from 'primeng/inputnumber';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-venta-dialog',
  standalone: true,
  imports: [CommonModule, DialogModule, InputNumberModule, ButtonModule, FormsModule],
  templateUrl: './venta-dialog.component.html',
  styleUrl: './venta-dialog.component.css'
})
export class VentaDialogComponent {
  @Input() visible = false;
  @Input() medicamento: Medicamento | null = null;

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() ventaRealizada = new EventEmitter<Medicamento>();

  cantidad = 1;

  constructor(private medicamentoService: MedicamentoService) {}

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
          this.ventaRealizada.emit(medActualizado);
          this.cerrar();
        },
        error: (err) => console.error('Error en venta', err)
      });
  }
}
