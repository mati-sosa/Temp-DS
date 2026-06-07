package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

/**
 * Estado de avance de una misión para un donante: su progreso actual, su estado y,
 * si se completó, la fecha en que ocurrió (clave para el ranking mensual de P5).
 */
@Getter
public class MisionEnProgreso {
  private final Mision mision;
  private EstadoMision estado;
  private LocalDate fechaCompletada;

  public MisionEnProgreso(Mision mision) {
    this.mision = mision;
    this.estado = EstadoMision.EN_PROGRESO;
  }

  public int progreso(List<EventoDonacion> historial) {
    return Math.min(mision.calcularProgreso(historial), mision.getObjetivo());
  }

  public int distanciaRestante(List<EventoDonacion> historial) {
    return Math.max(mision.getObjetivo() - mision.calcularProgreso(historial), 0);
  }

  public boolean estaCompletada(List<EventoDonacion> historial) {
    return mision.estaCompletada(historial);
  }

  public void completar() {
    this.estado = EstadoMision.COMPLETADA;
    this.fechaCompletada = LocalDate.now();
  }
}
