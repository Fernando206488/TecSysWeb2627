package edu.uclm.esi.tysweb.bealquileres.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CartoCiudadResponse(List<FeatureDto> features) {
}
