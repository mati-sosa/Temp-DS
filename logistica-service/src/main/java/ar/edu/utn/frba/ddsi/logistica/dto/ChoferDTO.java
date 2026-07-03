package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoferDTO {
    @NotBlank(message = "El id del chofer es requerido")
    private String id;

    @NotBlank(message = "El nombre del chofer es requerido")
    private String nombre;
}
