package ar.edu.utn.frba.ddsi.incentivos.models.eventos;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import java.time.LocalDateTime;
import lombok.Getter;

/** Se emite cuando un donante asciende de categoría. Dispara notificación al donante (P3). */
@Getter
public class CambioCategoria implements EventoIncentivos {
  private final Long donanteId;
  private final String user;
  private final Categoria nuevaCategoria;
  private final LocalDateTime timestamp;

  public CambioCategoria(Long donanteId, String user, Categoria nuevaCategoria) {
    this.donanteId = donanteId;
    this.user = user;
    this.nuevaCategoria = nuevaCategoria;
    this.timestamp = LocalDateTime.now();
  }
}
