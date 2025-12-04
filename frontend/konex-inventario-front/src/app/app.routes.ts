import { Routes } from '@angular/router';
import { MedicamentoListComponent } from './features/medicamentos/medicamento-list/medicamento-list.component';
import { MedicamentoFormComponent } from './features/medicamentos/medicamento-form/medicamento-form.component';
import { VentaListComponent } from './features/ventas/venta-list/venta-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'medicamentos', pathMatch: 'full' },
  { path: 'medicamentos', component: MedicamentoListComponent },
  { path: 'medicamentos/nuevo', component: MedicamentoFormComponent },
  { path: 'medicamentos/:id/editar', component: MedicamentoFormComponent },
  { path: 'ventas', component: VentaListComponent },
  { path: '**', redirectTo: 'medicamentos' }
];
