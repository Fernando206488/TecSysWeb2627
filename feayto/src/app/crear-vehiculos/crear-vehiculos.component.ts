import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { VehiculoService } from '../services/vehiculo.service';
import { VehiculoDto } from '../model/VehiculoDto';

@Component({
  selector: 'app-crear-vehiculos',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './crear-vehiculos.component.html',
  styleUrl: './crear-vehiculos.component.scss'
})
export class CrearVehiculosComponent implements OnInit{
  
  numberOfVehicles : number = 5;
  vehiculos : VehiculoDto[] = []

  constructor(private service : VehiculoService) {}

  ngOnInit(): void {
      this.getVehiculos()
  }

  createVehicles() {
    this.service.createVehicles(this.numberOfVehicles).subscribe(
      ok=> {
        this.getVehiculos()
      },
      error => {
        alert(error.error.message)
      }
    )
  }

  getVehiculos() {
    this.service.getVehiculos().subscribe(
      ok=> {
        this.vehiculos = ok
      }
    )
  }
}
