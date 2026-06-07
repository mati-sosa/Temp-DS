package ar.edu.utn.frba.ddsi.donaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class NecesidadResponseDTO {
    private String tipo;
    private String descripcion;
    private String subcategoriaDescripcion;
    private Double cantidadRequerida;
    private double cantidadCubierta;
    private Boolean estaSatisfecha;
}
