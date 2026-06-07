package ar.edu.utn.frba.ddsi.donaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public class EntidadBeneficiariaResponseDTO {
    private Long id;
    private String tipoEntidad;
    private String razonSocial;
    private DireccionDTO direccion;
    private MedioDeContactoDTO telefono;
    private List<RepresentanteDTO> representantes;
    private List<NecesidadResponseDTO> necesidades;
}
