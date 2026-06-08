package ar.edu.utn.frba.ddsi.incentivos.models.eventos;

import java.time.LocalDate;
import lombok.Getter;

/**
 * Dato de una donación que llega al Servicio de Incentivos para impactar en el progreso de
 * las misiones. Es el contrato de integración entrante: el Servicio de Donaciones lo envía
 * (POST /incentivos/donantes/{id}/donaciones) al confirmar una donación.
 */
@Getter
public class EventoDonacion {
  private final LocalDate fecha;
  private final String categoria;
  private final int cantidadBienes;
  private final boolean exitosa;

  public EventoDonacion(LocalDate fecha, String categoria, int cantidadBienes, boolean exitosa) {
    if (fecha == null) {
      throw new IllegalArgumentException("La donación debe tener una fecha");
    }
    if (categoria == null || categoria.isBlank()) {
      throw new IllegalArgumentException("La donación debe tener una categoría");
    }
    if (cantidadBienes < 0) {
      throw new IllegalArgumentException("La cantidad de bienes no puede ser negativa");
    }
    this.fecha = fecha;
    this.categoria = categoria;
    this.cantidadBienes = cantidadBienes;
    this.exitosa = exitosa;
  }
}