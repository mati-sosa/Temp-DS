package ar.edu.utn.frba.ddsi.donaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class NecesidadRequestDTO {
    @NotBlank(message = "El tipo de necesidad es requerido: RECURRENTE o EXTRAORDINARIA")
    private String tipo;

    @NotBlank(message = "La descripción es requerida")
    private String descripcion;

    @NotBlank(message = "La descripción de la subcategoría es requerida")
    private String subcategoriaDescripcion;

    @NotBlank(message = "La descripción de la categoría es requerida")
    private String categoriaDescripcion;

    private boolean perecible;

    @NotBlank(message = "La unidad de medida es requerida")
    private String unidadDeMedida;

    @NotNull @Positive
    private Double cantidadRequerida;

    // Solo para NecesidadRecurrente
    @Positive
    private Double cantidadPorPeriodo;

    private String tipoPeriodo;

    @Positive
    private Integer cantidadPeriodo;
}
