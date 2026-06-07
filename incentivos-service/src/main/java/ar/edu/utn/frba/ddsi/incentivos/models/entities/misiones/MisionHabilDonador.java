package ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones;

import java.util.List;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;

/**
 * Hábil Donador: realizar una donación que supere X cantidad de bienes.
 * Progreso: la mayor cantidad de bienes donada en una sola donación.
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
