package ar.edu.utn.frba.ddsi.incentivos.models.eventos;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import java.time.LocalDateTime;
import lombok.Getter;

/** Se emite cuando un donante obtiene una insignia. Lo consumirá la difusión n8n (P5). */
@Getter
public class InsigniaObtenida implements EventoIncentivos {
  private final Long donanteId;
  private final String user;
  private final String insignia;
  private final Categoria categoria;
  private final String texto;
  private final LocalDateTime timestamp;

  public InsigniaObtenida(Long donanteId, String user, String insignia, Categoria categoria, String texto) {
    this.donanteId = donanteId;
    this.user = user;
    this.insignia = insignia;
    this.categoria = categoria;
    this.texto = texto;
    this.timestamp = LocalDateTime.now();
  }
}
