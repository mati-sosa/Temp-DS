package ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking;

import lombok.Getter;

/** Una posición dentro del ranking mensual de donantes. */
@Getter
public class PuestoRanking {
  private final int posicion;
  private final Long donanteId;
  private final String donanteUser;
  private final int misionesCompletadas;

  public PuestoRanking(int posicion, Long donanteId, String donanteUser, int misionesCompletadas) {
    if (posicion < 1) {
      throw new IllegalArgumentException("La posición debe ser mayor o igual a 1");
    }
    if (donanteId == null) {
      throw new IllegalArgumentException("El puesto debe referenciar a un donante");
    }
    this.posicion = posicion;
    this.donanteId = donanteId;
    this.donanteUser = donanteUser;
    this.misionesCompletadas = misionesCompletadas;
  }
}
