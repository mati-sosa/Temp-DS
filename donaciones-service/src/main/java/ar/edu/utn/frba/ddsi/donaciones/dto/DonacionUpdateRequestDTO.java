package ar.edu.utn.frba.ddsi.donaciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class DonacionUpdateRequestDTO {
    @NotNull(message = "El id de la entidad beneficiaria es requerido")
    private Long entidadBeneficiariaId;
}
