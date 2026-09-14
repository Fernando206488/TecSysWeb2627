import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { VehiculoDto } from '../model/VehiculoDto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  private readonly urlAlquileres= `${environment.bealquileresUrl}/vehiculos`;

  constructor() { }

  private http = inject(HttpClient);

  createVehicles(numberOfVehicles: number) {
    return this.http.post(`${this.urlAlquileres}/createVehicles`, numberOfVehicles)
  }

  getVehiculos() {
    console.log(`${this.urlAlquileres}/getVehiculos`)
    return this.http.get<VehiculoDto[]>(`${this.urlAlquileres}/getVehiculos`)
  }
}
