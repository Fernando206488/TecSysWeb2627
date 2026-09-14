package edu.uclm.esi.tysweb.simulador.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import edu.uclm.esi.tysweb.simulador.dto.RouteResponse;
import edu.uclm.esi.tysweb.simulador.exceptions.RouteException;

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

                try {

                        RouteResponse response = restClient.get()
                                        .uri(uriBuilder -> uriBuilder
                                                        .path("/services/api/route")
                                                        .queryParam("orig", origin)
                                                        .queryParam("dest", destination)
                                                        .queryParam("locale", "es")
                                                        .queryParam("vehicle", "CAR")
                                                        .build())
                                        .retrieve()
                                        .body(RouteResponse.class);

                        if (response == null) {
                                throw new RouteException(
                                                "CartoCiudad ha devuelto una respuesta vacía");
                        }

                        if (!Boolean.parseBoolean(response.found())) {
                                throw new RouteException(
                                                "No se ha encontrado una ruta entre "
                                                                + origin + " y " + destination);
                        }

                        return response;

                } catch (RestClientResponseException e) {

                        throw new RouteException(
                                        "Error HTTP de CartoCiudad: "
                                                        + e.getStatusCode()
                                                        + " - "
                                                        + e.getResponseBodyAsString());

                } catch (ResourceAccessException e) {

                        throw new RouteException(
                                        "No se ha podido conectar con CartoCiudad: "
                                                        + e.getMessage());

                } catch (RestClientException e) {

                        throw new RouteException(
                                        "Error consultando CartoCiudad: "
                                                        + e.getMessage());
                }
        }
}