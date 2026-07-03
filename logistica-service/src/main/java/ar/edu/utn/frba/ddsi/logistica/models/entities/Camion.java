package ar.edu.utn.frba.ddsi.logistica.models.entities;

import lombok.Getter;

@Getter
public class Camion {
    private final String patente;
    private final Float altura;
    private final Float capacidadVolumen;
    private final Float capacidadCarga;
    private final Boolean disponible;

    public Camion(String patente, Float altura, Float capacidadVolumen, Float capacidadCarga, Boolean disponible) {
        if (patente == null || patente.isBlank()) {
            throw new IllegalArgumentException("El camión debe tener patente");
        }
        this.patente = patente;
        this.altura = altura;
        this.capacidadVolumen = capacidadVolumen;
        this.capacidadCarga = capacidadCarga;
        this.disponible = disponible != null ? disponible : Boolean.TRUE;
    }

    public Boolean puedeTransportar(Float volumen, Float carga, Float altura) {
        return volumen <= capacidadVolumen && carga <= capacidadCarga && altura <= this.altura;
    }
}
