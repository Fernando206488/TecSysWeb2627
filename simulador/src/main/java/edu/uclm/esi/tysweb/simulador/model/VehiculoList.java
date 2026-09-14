package edu.uclm.esi.tysweb.simulador.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * VehiculoList
 */
public class VehiculoList {
    private List<Vehiculo> vehiculos = new CopyOnWriteArrayList<>();

    public void add(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    public void remove(Vehiculo vehiculo) {
        this.vehiculos.remove(vehiculo);
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public int size() {
        return this.vehiculos.size();
    }
}
