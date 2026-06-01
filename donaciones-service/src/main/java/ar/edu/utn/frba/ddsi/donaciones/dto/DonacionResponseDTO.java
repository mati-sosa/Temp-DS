package ar.edu.utn.frba.ddsi.donaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DonacionResponseDTO {
    private Long id;
    private String subcategoria;
    private int cantidadBienes;
    private String estadoActual;
}