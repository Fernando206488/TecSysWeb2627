package edu.uclm.esi.tysweb.simulador;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.uclm.esi.tysweb.simulador.services.SimuladorService;

@SpringBootApplication
public class SimuladorApplication { //implements CommandLineRunner {

    private final SimuladorService simuladorService;

    public SimuladorApplication(SimuladorService simuladorService) {
        this.simuladorService = simuladorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimuladorApplication.class, args);
    }

    /*@Override
    public void run(String... args) {
        simuladorService.simular("Ciudad Real", true);
    }*/
}