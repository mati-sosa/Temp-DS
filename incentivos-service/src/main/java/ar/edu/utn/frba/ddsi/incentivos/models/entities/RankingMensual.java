package ar.edu.utn.frba.ddsi.incentivos.models.entities;

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
    this.anio = anio;
    this.mes = mes;
    this.puestos = puestos;
    this.fechaPublicacion = LocalDate.now();
  }
}
