package ar.edu.utn.frba.ddsi.donaciones.models.entities.estados_donaciones;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuditoriaTransicion {
    private final String estadoAnterior;
    private final String estadoNuevo;
    private final LocalDateTime timestamp;
    private final String justificacion;

    public AuditoriaTransicion(String estadoAnterior, String estadoNuevo, String justificacion) {
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.timestamp = LocalDateTime.now();
        this.justificacion = justificacion;
    }
}