package edu.uclm.esi.tysweb.bealquileres.services;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import edu.uclm.esi.tysweb.bealquileres.dto.CartoCiudadResponse;
import edu.uclm.esi.tysweb.bealquileres.dto.FeatureDto;
import tools.jackson.databind.JsonNode;

@Component
public class CartoCiudadClient {

    private final RestClient restClient;

    public CartoCiudadClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api-features.idee.es")
                .build();
    }

    public List<FeatureDto> load(String municipio, Integer offset, Integer limit) {
        CartoCiudadResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/collections/address/items")
                    .queryParam("component_AddressAreaName", municipio)
                    .queryParam("f", "json")
                    .queryParam("offset", offset)
                    .queryParam("limit", limit)
                    .build())
                .retrieve()
                .body(CartoCiudadResponse.class);

        if (response == null || response.features() == null)
            return List.of();

        return response.features();
    }

    public Integer getNumeroDeDirecciones(String municipio) {
        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/collections/address/items")
                    .queryParam("component_AddressAreaName", municipio)
                    .queryParam("f", "json")
                    .queryParam("limit", 1)
                    .build())
                .retrieve()
                .body(JsonNode.class);

        if (response == null)
            return 0;

        return response.path("numberMatched").asInt();
    }
}