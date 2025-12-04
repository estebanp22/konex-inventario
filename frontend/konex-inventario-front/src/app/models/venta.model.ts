export interface Venta {
  id?: number;
  fechaHora: string;
  medicamentoId?: number;
  medicamentoNombre?: string;
  cantidad: number;
  valorUnitario: number;
  valorTotal: number;
}
