package edu.uclm.esi.tysweb.bealquileres.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema (description = "JSON tipo: {\"municipio\" : \"Ciudad Real\"}")
public record MunicipioRequest(String municipio) {

}
