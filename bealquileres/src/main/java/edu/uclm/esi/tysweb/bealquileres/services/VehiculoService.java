package edu.uclm.esi.tysweb.bealquileres.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.tysweb.bealquileres.dao.VehiculoDao;
import edu.uclm.esi.tysweb.bealquileres.dto.VehiculoDto;
import edu.uclm.esi.tysweb.bealquileres.model.Vehiculo;

@Service 
public class VehiculoService {

    @Autowired 
    private VehiculoDao dao;

    public void createVehicles(Integer numberOfVehicles) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        for (int i=0; i<numberOfVehicles; i++)
            vehiculos.add(new Vehiculo());
        this.dao.saveAll(vehiculos);
    }

    public List<VehiculoDto> getVehiculos() {
        return this.dao.findAll().stream()
            .map(v -> new VehiculoDto(v.getId(), v.getMatricula(),
                v.getLatitud(), v.getLongitud(), v.getDistanciaTotal(), v.getBateria(),
                v.getActivo(), v.getMunicipio()!=null ? v.getMunicipio().getName() : null
                )).toList();
    }

}
