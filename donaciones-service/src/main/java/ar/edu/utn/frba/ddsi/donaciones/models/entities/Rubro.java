package ar.edu.utn.frba.ddsi.donaciones.models.entities;

import lombok.Getter;

@Getter
public class Rubro {
    private String descripcion;

    public Rubro(String descripcion) {
        if (descripcion == null) {
            throw new IllegalArgumentException("¡La descripción del rubro no puede estar vacía!");
        }
        this.descripcion = descripcion;
    } 
}
