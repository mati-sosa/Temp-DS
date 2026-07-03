package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CamionDTO {
    @NotBlank(message = "La patente del camión es requerida")
    private String patente;

    @NotNull @Positive(message = "La altura debe ser positiva")
    private Float altura;

    @NotNull @Positive(message = "La capacidad de volumen debe ser positiva")
    private Float capacidadVolumen;

    @NotNull @Positive(message = "La capacidad de carga debe ser positiva")
    private Float capacidadCarga;

    private Boolean disponible;
}
