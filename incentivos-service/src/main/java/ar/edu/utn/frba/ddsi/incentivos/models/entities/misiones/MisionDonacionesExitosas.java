package ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones;

import java.util.List;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;

/**
 * Donaciones Exitosas: lograr X donaciones recibidas exitosamente por una entidad beneficiaria.
 * Progreso: cantidad de donaciones marcadas como exitosas.
 */
public class MisionDonacionesExitosas extends Mision {

  public MisionDonacionesExitosas(String nombre, String descripcion, Categoria categoria, int orden, int objetivoExitosas) {
    super(nombre, descripcion, categoria, orden, objetivoExitosas);
  }

  @Override
  public int calcularProgreso(List<EventoDonacion> historial) {
    return (int) historial.stream()
        .filter(EventoDonacion::isExitosa)
        .count();
  }
}
