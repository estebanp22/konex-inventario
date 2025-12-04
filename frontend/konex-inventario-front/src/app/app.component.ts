import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, MenubarModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  items: MenuItem[] = [
    { label: 'Medicamentos', routerLink: ['/medicamentos'], icon: 'pi pi-list' },
    { label: 'Ventas', routerLink: ['/ventas'], icon: 'pi pi-shopping-cart' }
  ];
}
