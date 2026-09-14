package edu.uclm.esi.tysweb.simulador.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Instruction(List<String> bbox, String description, List<Double> dest, String distance, String indication, List<Double> orig) {
}