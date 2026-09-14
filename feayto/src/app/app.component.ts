import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MapaComponent } from "./mapa/mapa.component";
import { CrearVehiculosComponent } from "./crear-vehiculos/crear-vehiculos.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MapaComponent, CrearVehiculosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'feayto';
  selectedOption = 0

  select(option: number) {
    this.selectedOption = option
  }

}
