package ar.edu.utn.frba.ddsi.donaciones.models.entities.donantes.juridico;

import lombok.Getter;

@Getter
public class Representante {
    private String nombre;

    public Representante(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("¡El representante debe tener un nombre!");
        }
        this.nombre = nombre;
    }
}
