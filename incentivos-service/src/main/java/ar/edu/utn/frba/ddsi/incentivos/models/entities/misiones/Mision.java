package ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones;

import java.util.List;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import lombok.Getter;

/**
 * Misión a completar por el donante. Es abstracta: cada tipo de misión calcula su progreso
 * de forma distinta (Strategy vía herencia). El objetivo es la meta a alcanzar.
 */
@Getter
public abstract class Mision {
  private final String nombre;
  private final String descripcion;
  private final Categoria categoria;
  private final int orden;
  private final int objetivo;
  // TODO: pensar si la insignia debe ser parte de la Misión,
  // o si se genera dinámicamente al completarla
  // private final Insignia insignia;

  // TODO: validaciones de campos
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
