package edu.uclm.esi.tysweb.bealquileres.services;

import edu.uclm.esi.tysweb.bealquileres.dto.VehiculoSimulador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 MapaService
*/


@Service
public class MapaService {
    @Autowired
    private SimuladorClient simulador;

    public List<VehiculoSimulador> getVehiculos(String city) {
        return this.simulador.getVehiculos(city);
    }

}

record VehiculoDto(Long id, String matricula, Double latitudActual, Double longitudActual,
    Double distanciaTotal, Double bateria, Boolean activo, String municipio) {

}

