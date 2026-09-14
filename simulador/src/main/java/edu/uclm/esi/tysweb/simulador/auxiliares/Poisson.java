package edu.uclm.esi.tysweb.simulador.auxiliares;

import java.util.Random;

public class Poisson {
    private static final Random RANDOM = new Random();

    public static double velocidadAleatoria() {
        final double lambda = 15.0;

        int valor;

        do {
            valor = poisson(lambda);
        } while (valor < 6 || valor > 30);

        return (double) valor;
    }

    private static int poisson(double lambda) {

        double l = Math.exp(-lambda);
        int k = 0;
        double p = 1.0;

        do {
            k++;
            p *= RANDOM.nextDouble();
        } while (p > l);

        return k - 1;
    }
}