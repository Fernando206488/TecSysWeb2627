package edu.uclm.esi.tysweb.simulador.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import edu.uclm.esi.tysweb.simulador.dto.RouteResponse;

@Service
public class CartoCiudadRouteClient {

    private final RestClient restClient;

    public CartoCiudadRouteClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://www.cartociudad.es")
                .build();
    }

    public RouteResponse getRoute(
            Double originLatitude,
            Double originLongitude,
            Double destinationLatitude,
            Double destinationLongitude) {

        String origin = originLongitude + "," + originLatitude;
        String destination = destinationLongitude + "," + destinationLatitude;

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/services/api/route")
                        .queryParam("orig", origin)
                        .queryParam("dest", destination)
                        .queryParam("locale", "es")
                        .queryParam("vehicle", "CAR")
                        .build())
                .retrieve()
                .body(RouteResponse.class);
    }
}