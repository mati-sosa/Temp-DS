package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class DestinoRequestDTO {
    @NotNull @Valid
    private DireccionDTO direccion;

    @NotBlank(message = "El id de la entidad beneficiaria es requerido")
    private String entidadBeneficiariaID;

    @NotNull(message = "El orden del destino dentro de la ruta es requerido")
    private Integer orden;

    @NotEmpty(message = "El destino debe tener al menos una entrega")
    @Valid
    private List<EntregaRequestDTO> entregas;
}
