package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    @NotBlank(message = "La calle es requerida")
    private String calle;

    @NotBlank(message = "La ciudad es requerida")
    private String ciudad;

    @NotBlank(message = "La provincia es requerida")
    private String provincia;

    private String codigoPostal;
}
