import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Venta } from '../models/venta.model';
import { PageResponse } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private readonly baseUrl = 'http://localhost:8080/api/ventas';

  constructor(private http: HttpClient) {}

  getVentas(fechaInicio: string, fechaFin: string, page = 0, size = 10): Observable<PageResponse<Venta>> {
    const params = new HttpParams()
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin)
      .set('page', page)
      .set('size', size);

    return this.http.get<PageResponse<Venta>>(this.baseUrl, { params });
  }
}
