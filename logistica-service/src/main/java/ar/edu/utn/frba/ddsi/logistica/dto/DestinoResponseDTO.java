package ar.edu.utn.frba.ddsi.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DestinoResponseDTO {
    private DireccionDTO direccion;
    private String entidadBeneficiariaID;
    private Integer orden;
    private List<EntregaResponseDTO> entregas;
}
