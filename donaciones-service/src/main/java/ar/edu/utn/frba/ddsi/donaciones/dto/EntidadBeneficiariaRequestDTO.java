package ar.edu.utn.frba.ddsi.donaciones.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class EntidadBeneficiariaRequestDTO {
    @NotBlank(message = "El tipo de entidad es requerido")
    private String tipoEntidad;

    @NotBlank(message = "La descripción del tipo de entidad es requerida")
    private String descripcionTipo;

    @NotBlank(message = "La razón social es requerida")
    private String razonSocial;

    @NotNull @Valid
    private DireccionDTO direccion;

    @NotNull @Valid
    private MedioDeContactoDTO telefono;

    @NotEmpty(message = "Debe indicarse al menos un representante")
    @Valid
    private List<RepresentanteDTO> representantes;
}
