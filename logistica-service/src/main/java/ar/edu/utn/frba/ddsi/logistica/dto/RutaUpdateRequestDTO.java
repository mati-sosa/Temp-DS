package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class RutaUpdateRequestDTO {
    @NotNull @Valid
    private ChoferDTO chofer;

    @NotNull @Valid
    private CamionDTO camion;

    @NotNull(message = "La fecha de reparto es requerida")
    private LocalDateTime fechaReparto;
}
