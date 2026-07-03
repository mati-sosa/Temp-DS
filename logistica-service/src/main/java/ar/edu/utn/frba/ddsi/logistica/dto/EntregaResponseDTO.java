package ar.edu.utn.frba.ddsi.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class EntregaResponseDTO {
    private Long id;
    private String estado;
    private LocalDateTime fechaHoraEntrega;
    private List<String> fotos;
    private String donacionID;
}
