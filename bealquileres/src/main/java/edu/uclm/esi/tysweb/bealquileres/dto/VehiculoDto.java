package edu.uclm.esi.tysweb.bealquileres.dto;

/**
 * VehiculoDto
 */
public record VehiculoDto(Long id, String matricula, Double latitudActual, Double longitudActual,
    Double distanciaTotal, Double bateria, Boolean activo, String municipio) {

}
