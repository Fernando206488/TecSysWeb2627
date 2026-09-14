package edu.uclm.esi.tysweb.bealquileres.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.tysweb.bealquileres.model.Municipio;

public interface MunicipioDao extends JpaRepository<Municipio, String> {

    Municipio findByName(String sMunicipio);

}