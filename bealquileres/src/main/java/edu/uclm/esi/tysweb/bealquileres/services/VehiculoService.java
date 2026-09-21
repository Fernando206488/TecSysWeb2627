package edu.uclm.esi.tysweb.bealquileres.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import edu.uclm.esi.tysweb.bealquileres.dao.VehiculoDao;
import edu.uclm.esi.tysweb.bealquileres.dao.MunicipioDao;
import edu.uclm.esi.tysweb.bealquileres.dto.VehiculoDto;
import edu.uclm.esi.tysweb.bealquileres.model.Municipio;
import edu.uclm.esi.tysweb.bealquileres.model.Vehiculo;

@Service 
public class VehiculoService {

    @Autowired 
    private VehiculoDao dao;

    @Autowired
    private MunicipioDao municipioDao;

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

    //Se accede al dao para poder localizar la bicicleta que se desea y en caso de que este se envía
    public void asignarVehiculo(String municipio, String idVehiculo) {

        Municipio m = municipioDao.findByName(municipio);

        //En caso de que no encuentre el municipio seleccionado, lanza el error para comunicar que no está en la base de datos.
        if(m == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El municipio seleccionado no se encuentra en nuestra base de datos...");
        }

        //Se accede al dao para poder localizar la bicicleta que se desea y en caso de que este se envía
        this.dao.findById(idVehiculo).ifPresent(
            v -> {
                v.setMunicipio(m);
                v.setActivo(true);
                this.dao.save(v);
            });
    }

}
