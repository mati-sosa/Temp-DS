package ar.edu.utn.frba.ddsi.donaciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CambioEstadoRequestDTO {
    @NotNull(message = "La acción es requerida")
    private AccionEstado accion;
    private String justificacion; // requerido solo para FALLAR
}