package edu.uclm.esi.tysweb.bealquileres.dto;

public record VehiculoSimulador(String matricula, 
    double latitudOrigen, double longitudOrigen,String nombreOrigen, 
    double latitudDestino, double longitudDestino, String nombreDestino, 
    double latitudActual, double longitudActual,String direccion,
    double kmh, double distanciaRecorrida
) {

}
