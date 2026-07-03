package ar.edu.utn.frba.ddsi.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class RutaResponseDTO {
    private Long id;
    private ChoferDTO chofer;
    private CamionDTO camion;
    private LocalDateTime fechaReparto;
    private String estado;
    private List<DestinoResponseDTO> destinos;
}
