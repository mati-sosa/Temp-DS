package ar.edu.utn.frba.ddsi.donaciones.dto;

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
public class DonacionTotalRequestDTO {
    @NotBlank(message = "La descripción es requerida")
    private String descripcion;

    @NotNull(message = "El id del donante es requerido")
    private Long donanteId;

    @NotEmpty(message = "Debe incluirse al menos un bien")
    @Valid
    private List<BienRequestDTO> bienes;
}