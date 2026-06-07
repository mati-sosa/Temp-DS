package ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones;

import java.util.List;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;

/**
 * Completitud: realizar donaciones de X categorías distintas.
 * Progreso: cantidad de categorías distintas donadas.
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