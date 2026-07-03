package ar.edu.utn.frba.ddsi.logistica.models.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PosicionCamion {
    private final Camion camion;
    private final Float latitud;
    private final Float longitud;
    private final LocalDateTime timestamp;
    private final Float velocidad;

    public PosicionCamion(Camion camion, Float latitud, Float longitud, Float velocidad) {
        this.camion = camion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.velocidad = velocidad;
        this.timestamp = LocalDateTime.now();
    }
}
