package ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes;

import lombok.Getter;

@Getter
public class CantidadDeBien {
    private double cantidad;
    private UnidadDeMedida unidadDeMedida;

    public CantidadDeBien(double cantidad, UnidadDeMedida unidadDeMedida) {
        this.cantidad = cantidad;
        this.unidadDeMedida = unidadDeMedida;
    }
}
