package edu.uclm.esi.tysweb.simulador.http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.tysweb.simulador.dto.MunicipioDto;
import edu.uclm.esi.tysweb.simulador.dto.PuntoRutaDto;
import edu.uclm.esi.tysweb.simulador.dto.VehiculoDto;
import edu.uclm.esi.tysweb.simulador.services.SimuladorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vehiculos")
@CrossOrigin("*")
@Tag(
    name = "Vehículos",
    description = "Operaciones relacionadas con la simulación de la circulación de bicicletas"
)
public class VehiculosController {

    @Autowired
    private SimuladorService service;

    @GetMapping("/getCities") @Operation(summary = "Devuelve las ciudades dadas de alta")
    public List<MunicipioDto> getCities() {
        return this.service.getCities();
    }

     @PostMapping("/simulacionAleatoria")
    @Operation(
        summary = "Genera bicicletas en coordenadas aleatorias para la ciudad pasada como parámetro"
    )
    public void simulacionAleatoria(@RequestParam @Parameter(description = "Nombre de la ciudad") String city) {
        this.service.simulacionAleatoria(city);
    }

    @PostMapping("/generateBicycle")
    @Operation(
        summary = "Genera una bicicleta en coordenadas aleatorias para la ciudad pasada como parámetro"
    )
    public void generateBicycle(@RequestParam @Parameter(description = "Nombre de la ciudad") String city) {
        this.service.generateBicycle(city);
    }

    @GetMapping("/getVehiculos/{city}")
    @Operation(
        summary = "Recupera todos los vehículos que están circulando en la ciudad pasada como parámetro"
    )
    public List<VehiculoDto> getVehiculos(@PathVariable @Parameter(description = "Nombre de la ciudad") String city) {
        return this.service.getVehiculos(city);
    }

    @GetMapping("/getRuta/{city}")
    public List<PuntoRutaDto> getRuta(@PathVariable @Parameter(description = "Nombre de la ciudad") String city,
             @RequestParam @Parameter(description = "Matrícula del vehículo") Integer matricula) {
        return this.service.getRuta(city, matricula);
    }
}
