package edu.uclm.esi.tysweb.simulador.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InstructionsData(List<Instruction> instruction) {
}