package ar.edu.utn.frba.ddsi.donaciones.dto;

import ar.edu.utn.frba.ddsi.donaciones.models.entities.bienes.UnidadDeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class BienRequestDTO {
    @NotBlank(message = "La descripción del bien es requerida")
    private String descripcion;

    @NotBlank(message = "La subcategoría es requerida")
    private String subcategoriaDescripcion;

    @NotBlank(message = "La categoría es requerida")
    private String categoriaDescripcion;

    @NotNull(message = "La unidad de medida es requerida")
    private UnidadDeMedida unidadMedida;

    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Double cantidad;

    private String foto;

    @NotNull(message = "Debe indicarse si el bien es perecedero")
    private Boolean perecedero;

    private LocalDate fechaVencimiento; // requerido si perecedero = true
    private Boolean tieneEstado;
    private Boolean esNuevo;
}