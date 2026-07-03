package ar.edu.utn.frba.ddsi.logistica.models.entities;

import lombok.Getter;

@Getter
public class Chofer {
    private final String id;
    private final String nombre;

    public Chofer(String id, String nombre) {
        if (id == null || id.isBlank() || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El chofer debe tener id y nombre");
        }
        this.id = id;
        this.nombre = nombre;
    }
}
