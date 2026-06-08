package ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

/** Ranking de donantes de un mes, según la cantidad de misiones cumplidas en ese período. */
@Getter
public class RankingMensual {
  private final int anio;
  private final int mes;
  private final List<PuestoRanking> puestos;
  private final LocalDate fechaPublicacion;

  public RankingMensual(int anio, int mes, List<PuestoRanking> puestos) {
    if (mes < 1 || mes > 12) {
      throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
    }
    if (puestos == null) {
      throw new IllegalArgumentException("El ranking debe tener una lista de puestos");
    }
    this.anio = anio;
    this.mes = mes;
    this.puestos = puestos;
    this.fechaPublicacion = LocalDate.now();
  }
}
