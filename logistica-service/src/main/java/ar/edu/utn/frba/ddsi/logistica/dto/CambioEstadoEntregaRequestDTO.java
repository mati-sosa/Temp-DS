package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CambioEstadoEntregaRequestDTO {
    @NotNull(message = "La acción es requerida")
    private AccionEstadoEntrega accion;

    private String justificacion; // requerido solo para MARCAR_NO_RECIBIDA

    private List<String> fotos; // requerido solo para CONFIRMAR_RECEPCION
}
