package ar.edu.utn.frba.ddsi.donaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuditoriaTransicionDTO {
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime timestamp;
    private String justificacion;
}