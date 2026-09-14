package edu.uclm.esi.tysweb.simulador.model;

import java.util.List;

import edu.uclm.esi.tysweb.simulador.dto.Instruction;

public class VehiculoThread implements Runnable {

    private static final int INTERVALO_SEGUNDOS = 10;

    private final Vehiculo vehiculo;

    public VehiculoThread(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    private double metrosPorTick() {
        return this.vehiculo.getMps() * INTERVALO_SEGUNDOS;
    }

    @Override
    public void run() {
        List<Instruction> instrucciones = vehiculo.getInstrucciones();

        if (instrucciones == null || instrucciones.isEmpty())
            return;

        int instructionIndex = 0;
        double recorridaEnInstruccion = 0.0;

        Instruction primera = instrucciones.get(0);

        vehiculo.setCurrentLongitude(primera.orig().get(0));
        vehiculo.setCurrentLatitude(primera.orig().get(1));

        while (instructionIndex < instrucciones.size()) {
            try {
                Thread.sleep(INTERVALO_SEGUNDOS * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            double distanciaDisponible = this.metrosPorTick();
            this.vehiculo.recalculateKmh();
            Instruction instruccionActual = null;

            while (distanciaDisponible > 0 && instructionIndex < instrucciones.size()) {
                Instruction instruccion = instrucciones.get(instructionIndex);
                instruccionActual = instruccion;

                double distanciaInstruccion = parseDistance(instruccion.distance());
                double distanciaRestante = distanciaInstruccion - recorridaEnInstruccion;
                double avance = Math.min(distanciaDisponible, distanciaRestante);

                recorridaEnInstruccion += avance;
                distanciaDisponible -= avance;

                vehiculo.addDistanciaRecorrida(avance);

                double porcentaje = recorridaEnInstruccion / distanciaInstruccion;

                actualizarPosicion(instruccion, porcentaje);

                if (recorridaEnInstruccion >= distanciaInstruccion) {
                    vehiculo.setCurrentLongitude(instruccion.dest().get(0));
                    vehiculo.setCurrentLatitude(instruccion.dest().get(1));

                    instructionIndex++;
                    recorridaEnInstruccion = 0.0;
                }
            }

            if (instruccionActual != null) {
                vehiculo.setCurrentStreet(instruccionActual.description());
                //mostrarPosicion();
            }
        }
    }

    private void actualizarPosicion(Instruction instruccion, double porcentaje) {
        double origLon = instruccion.orig().get(0);
        double origLat = instruccion.orig().get(1);

        double destLon = instruccion.dest().get(0);
        double destLat = instruccion.dest().get(1);

        vehiculo.setCurrentLongitude(origLon + (destLon - origLon) * porcentaje);
        vehiculo.setCurrentLatitude(origLat + (destLat - origLat) * porcentaje);
    }

    private void mostrarPosicion() {
        System.out.printf(
                "Posición: %.6f, %.6f - %s - Distancia recorrida: %.2f m%n",
                vehiculo.getCurrentLatitude(),
                vehiculo.getCurrentLongitude(),
                vehiculo.getCurrentStreet(),
                vehiculo.getDistanciaRecorrida());
    }

    private double parseDistance(String distance) {
        String value = distance
                .trim()
                .toLowerCase()
                .replace(",", ".");

        if (value.endsWith("km"))
            return Double.parseDouble(value.replace("km", "").trim()) * 1000.0;

        return Double.parseDouble(value.replace("m", "").trim());
    }
}