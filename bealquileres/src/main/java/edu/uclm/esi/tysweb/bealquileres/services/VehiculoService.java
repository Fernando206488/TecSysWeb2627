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

    //Metodo llamado desde VehiculoController, accede al dao para poder localizar la bicicleta que se desea y en caso de que esté, se envía
    public void asignarVehiculos(String city, Integer cantidad) {
        Municipio municipio = this.municipioDao.findByName(city);

        //En caso de que no encuentre el municipio seleccionado, lanza el error para comunicar que no está en la base de datos.
        if(municipio == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El municipio seleccionado no se encuentra en nuestra base de datos...");
        }

        //Obtenemos todas las bicis libres y comprobamos que quedan mas de la cantidad indicada, sino lanzamos error por falta de bicis
        List<Vehiculo> bicisLibres = this.dao.getBicisLibres(cantidad);
        if(bicisLibres.size() < cantidad) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se disponen de " + bicisLibres.size() + " bicicletas libres en este momento. No se puede asignar la cantidad de " + cantidad + " bicicletas.");
        }

        for(Vehiculo bici : bicisLibres) {
            bici.setMunicipio(municipio);
        }
        
        // Guardamos todas las bicis asignadas
        this.dao.saveAll(bicisLibres);
    }

}
