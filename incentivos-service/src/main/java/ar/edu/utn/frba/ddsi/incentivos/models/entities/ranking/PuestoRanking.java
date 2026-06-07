package ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking;

import lombok.Getter;

/** Una posición dentro del ranking mensual de donantes. */
@Getter
public class PuestoRanking {
  private final int posicion;
  private final Long donanteId;
  private final String donanteUser;
  private final int misionesCompletadas;

  // TODO: validaciones de campos
  public PuestoRanking(int posicion, Long donanteId, String donanteUser, int misionesCompletadas) {
    this.posicion = posicion;
    this.donanteId = donanteId;
    this.donanteUser = donanteUser;
    this.misionesCompletadas = misionesCompletadas;
  }
}
