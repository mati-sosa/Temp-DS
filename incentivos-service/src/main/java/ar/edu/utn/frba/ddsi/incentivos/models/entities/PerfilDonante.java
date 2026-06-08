package ar.edu.utn.frba.ddsi.incentivos.models.entities;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.Mision;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.MisionEnProgreso;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import lombok.Getter;

/**
 * Perfil de incentivos de un donante (no duplica los datos del Servicio de Donaciones,
 * solo lo relativo a la gamificación). Es la raíz del agregado: contiene el historial de
 * donaciones que impacta en incentivos, la misión actual, las completadas y las insignias.
 */
@Getter
public class PerfilDonante {
  private final Long donanteId;
  private final String user;
  private Categoria categoria;
  private final List<EventoDonacion> historial;
  private final List<MisionEnProgreso> misionesCompletadas;
  private final List<Insignia> insignias;
  private MisionEnProgreso misionActual;

  public PerfilDonante(Long donanteId, String user, Mision primeraMision) {
    if (donanteId == null) {
      throw new IllegalArgumentException("El perfil debe referenciar a un donante");
    }
    if (user == null || user.isBlank()) {
      throw new IllegalArgumentException("El perfil debe tener un nombre de usuario");
    }
    if (primeraMision == null) {
      throw new IllegalArgumentException("El perfil debe arrancar con una misión");
    }
    this.donanteId = donanteId;
    this.user = user;
    this.categoria = Categoria.COLABORADOR;
    this.historial = new ArrayList<>();
    this.misionesCompletadas = new ArrayList<>();
    this.insignias = new ArrayList<>();
    this.misionActual = new MisionEnProgreso(primeraMision);
  }

  public void agregarDonacion(EventoDonacion evento) {
    this.historial.add(evento);
  }

  public boolean misionActualCompletada() {
    return misionActual != null && misionActual.estaCompletada(historial);
  }

  /**
   * Completa la misión actual: la marca como completada, otorga su insignia, la archiva y
   * pasa a la siguiente (o a null si no hay). Devuelve la insignia otorgada.
   */
  public Insignia completarMisionActual(Mision siguiente) {
    misionActual.completar();

    // TODO: pensar si la insignia debe pertenecer a la misión completada o al perfil del donante
    Insignia insignia = new Insignia(
        "Insignia: " + misionActual.getMision().getNombre(),
        misionActual.getMision().getCategoria());
    insignias.add(insignia);

    misionesCompletadas.add(misionActual);
    misionActual = (siguiente == null) ? null : new MisionEnProgreso(siguiente);

    return insignia;
  }

  public void subirCategoria(Categoria nueva) {
    this.categoria = nueva;
  }

  /** Cantidad de misiones completadas dentro de un período (año/mes). Lo usa el ranking de P5. */
  public int misionesCompletadasEn(int anio, int mes) {
    YearMonth periodo = YearMonth.of(anio, mes);

    return (int) misionesCompletadas.stream()
        .filter(m -> m.getFechaCompletada() != null
            && YearMonth.from(m.getFechaCompletada()).equals(periodo))
        .count();
  }

  public int totalMisionesCompletadas() {
    return misionesCompletadas.size();
  }
}