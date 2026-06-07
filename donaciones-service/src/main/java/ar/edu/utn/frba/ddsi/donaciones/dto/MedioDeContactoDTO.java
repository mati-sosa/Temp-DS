package ar.edu.utn.frba.ddsi.donaciones.dto;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.contacto.TipoMedioContacto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedioDeContactoDTO {
    @NotNull(message = "El tipo de medio de contacto es requerido")
    private TipoMedioContacto tipo;

    @NotBlank(message = "El valor del medio de contacto es requerido")
    private String valor;
}