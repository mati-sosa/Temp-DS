package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.time.LocalDate;
import lombok.Getter;

/**
 * Representa el dato de una donación que llega al Servicio de Incentivos para impactar
 * en el progreso de las misiones. Es el "contrato" que en la integración real proveerá
 * el Servicio de Donaciones (por ahora se carga a mano / desde tests).
 */
@Getter
public class EventoDonacion {
  private final LocalDate fecha;
  private final String categoria;
  private final int cantidadBienes;
  private final boolean exitosa;

  public EventoDonacion(LocalDate fecha, String categoria, int cantidadBienes, boolean exitosa) {
    this.fecha = fecha;
    this.categoria = categoria;
    this.cantidadBienes = cantidadBienes;
    this.exitosa = exitosa;
  }
}
