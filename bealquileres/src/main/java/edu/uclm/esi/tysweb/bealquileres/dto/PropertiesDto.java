package edu.uclm.esi.tysweb.bealquileres.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PropertiesDto(
        @JsonProperty("component_ThoroughfareName") String componentThoroughfareName,
        @JsonProperty("locator_designator_addressNumber") String locatorDesignatorAddressNumber,
        @JsonProperty("component_PostalDescriptor") String componenPostalDescriptor) {
}