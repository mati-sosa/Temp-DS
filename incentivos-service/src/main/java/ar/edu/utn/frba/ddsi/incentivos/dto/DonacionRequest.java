package ar.edu.utn.frba.ddsi.incentivos.dto;

import java.time.LocalDate;

/** Datos de una donación que llegan por la API para impactar en los incentivos del donante. */
public record DonacionRequest(LocalDate fecha, String categoria, int cantidadBienes, boolean exitosa) {
}
