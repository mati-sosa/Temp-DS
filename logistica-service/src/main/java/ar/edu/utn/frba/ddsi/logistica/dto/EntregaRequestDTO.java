package ar.edu.utn.frba.ddsi.logistica.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class EntregaRequestDTO {
    @NotBlank(message = "El id de la donación es requerido")
    private String donacionID;

    private LocalDateTime fechaHoraEntrega;
}
