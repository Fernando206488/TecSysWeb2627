package edu.uclm.esi.tysweb.bealquileres.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.tysweb.bealquileres.model.Feature;

public interface FeatureDao extends JpaRepository<Feature, String> {

}