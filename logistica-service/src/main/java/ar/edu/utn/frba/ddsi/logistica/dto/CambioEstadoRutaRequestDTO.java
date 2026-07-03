package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CambioEstadoRutaRequestDTO {
    @NotNull(message = "La acción es requerida")
    private AccionEstadoRuta accion;

    private String justificacion;
}
