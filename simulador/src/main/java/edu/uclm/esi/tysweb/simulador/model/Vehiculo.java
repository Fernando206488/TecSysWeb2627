package edu.uclm.esi.tysweb.simulador.model;

import java.util.ArrayList;
import java.util.List;

import edu.uclm.esi.tysweb.simulador.auxiliares.Poisson;
import edu.uclm.esi.tysweb.simulador.dto.Instruction;
import edu.uclm.esi.tysweb.simulador.dto.PuntoRutaDto;

public class Vehiculo {

    private Feature origen;
    private Feature destino;
    private List<Instruction> ruta;

    private Double currentLatitude;
    private Double currentLongitude;
    private String currentStreet;
    private Double distanciaRecorrida = 0.0;
    private int matricula;
    private double kmh;

    public Vehiculo(int matricula, Feature origen, Feature destino) {
        this.matricula = matricula;
        this.origen = origen;
        this.destino = destino;
        this.currentLatitude = origen.getLatitude();
        this.currentLongitude = origen.getLongitude();
        this.recalculateKmh();
    }

    public double getMps() {
        return kmh / 3.6;
    }

    public double getKmh() {
        return kmh;
    }

    public void recalculateKmh() {
        this.kmh = Poisson.velocidadAleatoria();
    }

    public Feature getOrigen() {
        return origen;
    }

    public Feature getDestino() {
        return destino;
    }

    public void setRuta(List<Instruction> ruta) {
        this.ruta = ruta;
    }

    public List<Instruction> getInstrucciones() {
        return this.ruta;
    }

    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getCurrentStreet() {
        return currentStreet;
    }

    public void setCurrentStreet(String currentStreet) {
        this.currentStreet = currentStreet;
    }

    public Double getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void addDistanciaRecorrida(double metros) {
        this.distanciaRecorrida += metros;
    }

    public int getMatricula() {
        return matricula;
    }

    public void arrancar(Runnable onFinish) {
        Thread thread = new Thread(() -> {
            try {
                VehiculoThread vehiculoThread = new VehiculoThread(this);
                vehiculoThread.run();
            } finally {
                onFinish.run();
            }
        });
        thread.start();
    }

    public List<PuntoRutaDto> getRuta() {
        List<PuntoRutaDto> puntos = new ArrayList<>();
        if (this.ruta == null || this.ruta.isEmpty()) 
            return puntos;

        Instruction primera = this.ruta.get(0);
        puntos.add(
                new PuntoRutaDto(
                        primera.orig().get(1),
                        primera.orig().get(0)));

        for (Instruction instruction : this.ruta) {
            puntos.add(
                    new PuntoRutaDto(
                            instruction.dest().get(1),
                            instruction.dest().get(0)));
        }

        return puntos;
    }
}