import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import {Toast, ToastModule} from 'primeng/toast';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MenubarModule, Toast, ToastModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  items: MenuItem[] = [
    { label: 'Medicamentos', routerLink: ['/medicamentos'], icon: 'pi pi-list' },
    { label: 'Ventas', routerLink: ['/ventas'], icon: 'pi pi-shopping-cart' }
  ];
}
