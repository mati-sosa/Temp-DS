package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.util.List;

/**
 * Completitud: realizar donaciones de X categorías distintas.
 * Progreso = cantidad de categorías distintas donadas.
 */
public class MisionCompletitud extends Mision {

  public MisionCompletitud(String nombre, String descripcion, Categoria categoria, int orden, int objetivoCategorias) {
    super(nombre, descripcion, categoria, orden, objetivoCategorias);
  }

  @Override
  public int calcularProgreso(List<EventoDonacion> historial) {
    return (int) historial.stream()
        .map(EventoDonacion::getCategoria)
        .distinct()
        .count();
  }
}
