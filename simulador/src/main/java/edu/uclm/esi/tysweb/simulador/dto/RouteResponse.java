package edu.uclm.esi.tysweb.simulador.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RouteResponse(String distance, String time, String found, String from, String to, InstructionsData instructionsData) {
}