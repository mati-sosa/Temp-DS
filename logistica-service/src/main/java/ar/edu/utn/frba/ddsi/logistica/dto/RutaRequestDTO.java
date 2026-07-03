package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class RutaRequestDTO {
    @NotNull @Valid
    private ChoferDTO chofer;

    @NotNull @Valid
    private CamionDTO camion;

    @NotNull(message = "La fecha de reparto es requerida")
    private LocalDateTime fechaReparto;

    @NotEmpty(message = "La ruta debe tener al menos un destino")
    @Valid
    private List<DestinoRequestDTO> destinos;
}
