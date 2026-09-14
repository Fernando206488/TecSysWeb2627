package edu.uclm.esi.tysweb.simulador.dto;

public class GeneracionElementDto {

    private int matricula;
    private boolean arrancado;
    private String error;

    public GeneracionElementDto(int matricula) {
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setArrancado(boolean arrancado) {
        this.arrancado = arrancado;
    }

    public boolean isArrancado() {
        return arrancado;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
