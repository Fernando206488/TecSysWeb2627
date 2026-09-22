package edu.uclm.esi.tysweb.bealquileres.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.tysweb.bealquileres.dao.FeatureDao;
import edu.uclm.esi.tysweb.bealquileres.dao.MunicipioDao;
import edu.uclm.esi.tysweb.bealquileres.dao.EstacionDao;
import edu.uclm.esi.tysweb.bealquileres.dto.FeatureDto;
import edu.uclm.esi.tysweb.bealquileres.model.Estacion;
import edu.uclm.esi.tysweb.bealquileres.model.Feature;
import edu.uclm.esi.tysweb.bealquileres.model.Municipio;

@Service
public class MunicipioService {

    private final CartoCiudadClient cartoCiudadClient;
    private final MunicipioDao dao;
    private final FeatureDao featureDao;
    private final EstacionDao estacionDao;

    @Autowired 
    private SseService sseService;

    public MunicipioService(CartoCiudadClient cartoCiudadClient, MunicipioDao dao, FeatureDao featureDao, EstacionDao estacionDao) {
        this.cartoCiudadClient = cartoCiudadClient;
        this.dao = dao;
        this.featureDao = featureDao;
        this.estacionDao = estacionDao;
    }

    public void load(String name, Integer offset, Integer limit, Boolean progress) {
        List<FeatureDto> features = cartoCiudadClient.load(name, offset, limit);
        Municipio municipio = this.dao.findByName(name);
        if (municipio==null) {
            municipio = new Municipio();
            municipio.setName(name);
            this.dao.save(municipio);
        }

        final int BATCH_SIZE = 500;
        int processed = 0;
        List<Feature> batch = new ArrayList<>(BATCH_SIZE);

        for (FeatureDto dto : features) {
            Feature feature = new Feature();
            feature.setIdeeId(dto.id());
            feature.setLatitude(dto.geometry().coordinates().get(0));
            feature.setLongitude(dto.geometry().coordinates().get(1));
            feature.setMunicipio(municipio);
            feature.setName(dto.properties().componentThoroughfareName());
            feature.setNumber(dto.properties().locatorDesignatorAddressNumber());
            feature.setZipCode(dto.properties().componenPostalDescriptor());

            processed++;
            batch.add(feature);
            if (batch.size()==BATCH_SIZE) {
                this.featureDao.saveAllAndFlush(batch);
                if (progress)
                    this.sseService.send(name, "progress", processed);
                batch.clear();
            }
        }

        if (!batch.isEmpty())
            this.featureDao.saveAllAndFlush(batch);

        if (progress)
            this.sseService.send(name, "completed", processed);

        System.out.println("Terminado: " + processed);
    }

    public Integer getNumeroDeDirecciones(String municipio) {
        return this.cartoCiudadClient.getNumeroDeDirecciones(municipio);
    }

    public void setEstacion(String city, String nombre, Integer number, Integer capacidad) {
        Municipio municipio = this.dao.findByName(city);
        if(municipio==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el municipio " + municipio);
        
        Feature feature = this.featureDao.findByMunicipioAndNameAndNumber(municipio, nombre, number, capacidad);

        if(feature == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la dirección " + nombre + " " + number + " en el municipio " + municipio);
        
        Estacion estacion = new Estacion();
        estacion.setMunicipio(municipio);
        estacion.setName(feature.getName());
        estacion.setNumero(feature.getNumber());
        estacion.setLatitude(feature.getLatitude());
        estacion.setLongitude(feature.getLongitude());
        estacion.setCapacidad(number);

        this.estacionDao.save(estacion);
    }
    
}
