package edu.uclm.esi.tysweb.simulador.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.tysweb.simulador.model.Municipio;

public interface MunicipioDao extends JpaRepository<Municipio, String> {

    Municipio findByName(String sMunicipio);

}