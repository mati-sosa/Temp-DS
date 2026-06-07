package ar.edu.utn.frba.ddsi.incentivos.models.eventos;

import java.time.LocalDateTime;
import lombok.Getter;

/** Se emite cuando un donante completa una misión. Dispara notificación al donante (P3). */
@Getter
public class MisionCumplida implements EventoIncentivos {
  private final Long donanteId;
  private final String user;
  private final String mision;
  private final LocalDateTime timestamp;

  public MisionCumplida(Long donanteId, String user, String mision) {
    this.donanteId = donanteId;
    this.user = user;
    this.mision = mision;
    this.timestamp = LocalDateTime.now();
  }
}
