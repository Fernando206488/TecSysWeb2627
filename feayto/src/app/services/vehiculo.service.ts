import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { VehiculoDto } from '../model/VehiculoDto';
import { PuntoRutaDto } from '../model/PuntoRutaDto';
import { MunicipioDto } from '../model/MunicipioDto';

@Injectable({
  providedIn: 'root'
})
export class VehiculosService {

  private http = inject(HttpClient);

  getCities() {
    return this.http.get<MunicipioDto[]>(`http://localhost:8082/vehiculos/getCities`)
  }

  simulacionAleatoria(city: string) {
    return this.http.post(`http://localhost:8082/vehiculos/simulacionAleatoria?city=${city}`, {})
  }

  generateBicycle(city: string) {
    return this.http.post(`http://localhost:8082/vehiculos/generateBicycle?city=${city}`, {})
  }

  getVehiculos(city? : string): Observable<VehiculoDto[]> {
    return this.http.get<VehiculoDto[]>(`http://localhost:8082/vehiculos/getVehiculos/${city}`);
  }

  getRuta(city : string, matricula: string): Observable<PuntoRutaDto[]> {
    return this.http.get<PuntoRutaDto[]>(
      `http://localhost:8082/vehiculos/getRuta/${city}?matricula=${matricula}`
    );
  }

}