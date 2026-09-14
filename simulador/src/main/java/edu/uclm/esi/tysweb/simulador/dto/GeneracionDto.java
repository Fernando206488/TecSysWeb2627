package edu.uclm.esi.tysweb.simulador.dto;

import java.util.ArrayList;
import java.util.List;

public class GeneracionDto {
    private String city;
    private String responseType;
    private String response;
    private List<GeneracionElementDto> elements = new ArrayList<>();

    public GeneracionDto(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public List<GeneracionElementDto> getElements() {
        return elements;
    }

    public void setElements(List<GeneracionElementDto> elements) {
        this.elements = elements;
    }

    public void addElement(GeneracionElementDto element) {
        this.elements.add(element);
    }
}
