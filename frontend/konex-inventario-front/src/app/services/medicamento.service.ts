import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../models/medicamento.model';
import { PageResponse } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class MedicamentoService {
  private readonly baseUrl = 'http://localhost:8080/api/medicamentos';

  constructor(private http: HttpClient) {}

  getMedicamentos(filtroNombre = '', page = 0, size = 10): Observable<PageResponse<Medicamento>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (filtroNombre) {
      params = params.set('filtroNombre', filtroNombre);
    }

    return this.http.get<PageResponse<Medicamento>>(this.baseUrl, { params });
  }

  getMedicamento(id: number): Observable<Medicamento> {
    return this.http.get<Medicamento>(`${this.baseUrl}/${id}`);
  }

  createMedicamento(m: Medicamento): Observable<Medicamento> {
    return this.http.post<Medicamento>(this.baseUrl, m);
  }

  updateMedicamento(id: number, m: Medicamento): Observable<Medicamento> {
    return this.http.put<Medicamento>(`${this.baseUrl}/${id}`, m);
  }

  deleteMedicamento(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  venderMedicamento(id: number, cantidad: number): Observable<Medicamento> {
    return this.http.post<Medicamento>(`${this.baseUrl}/${id}/vender`, { cantidad });
  }
}
