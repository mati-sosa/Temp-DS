package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.util.List;
import lombok.Getter;

/**
 * Misión a completar por el donante. Es abstracta: cada tipo de misión calcula su progreso
 * de forma distinta (Strategy vía herencia). El objetivo es la meta a alcanzar.
 *
 * <p>Extension point (P4 - Francisco): refinar las reglas finas de {@link #calcularProgreso}
 * por tipo de misión.
 */
@Getter
public abstract class Mision {
  private final String nombre;
  private final String descripcion;
  private final Categoria categoria;
  private final int orden;
  private final int objetivo;

  protected Mision(String nombre, String descripcion, Categoria categoria, int orden, int objetivo) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.orden = orden;
    this.objetivo = objetivo;
  }

  /** Progreso acumulado del donante para esta misión, dado su historial de donaciones. */
  public abstract int calcularProgreso(List<EventoDonacion> historial);

  public boolean estaCompletada(List<EventoDonacion> historial) {
    return calcularProgreso(historial) >= objetivo;
  }
}
