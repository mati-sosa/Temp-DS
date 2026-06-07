package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.util.List;

/**
 * Hábil Donador: realizar una donación que supere X cantidad de bienes.
 * Progreso = la mayor cantidad de bienes donada en una sola donación.
 */
public class MisionHabilDonador extends Mision {

  public MisionHabilDonador(String nombre, String descripcion, Categoria categoria, int orden, int objetivoBienes) {
    super(nombre, descripcion, categoria, orden, objetivoBienes);
  }

  @Override
  public int calcularProgreso(List<EventoDonacion> historial) {
    return historial.stream()
        .mapToInt(EventoDonacion::getCantidadBienes)
        .max()
        .orElse(0);
  }
}
