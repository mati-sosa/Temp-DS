package ar.edu.utn.frba.ddsi.incentivos.dto.mappers;

import ar.edu.utn.frba.ddsi.incentivos.dto.InsigniaResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.MisionResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.MisionesResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.PerfilResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.PuestoResponse;
import ar.edu.utn.frba.ddsi.incentivos.dto.RankingResponse;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.EstadoMision;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.MisionEnProgreso;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.Insignia;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.PerfilDonante;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking.PuestoRanking;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking.RankingMensual;
import java.util.List;
import org.springframework.stereotype.Component;

/** Traduce las entidades de dominio a los DTOs de la API (no se exponen las entidades directamente). */
@Component
public class IncentivosMapper {

  public PerfilResponse toPerfilResponse(PerfilDonante perfil) {
    MisionEnProgreso actual = perfil.getMisionActual();
    String misionActual = actual == null ? null : actual.getMision().getNombre();
    int progreso = actual == null ? 0 : actual.progreso(perfil.getHistorial());
    int objetivo = actual == null ? 0 : actual.getMision().getObjetivo();
    int distancia = actual == null ? 0 : actual.distanciaRestante(perfil.getHistorial());
    return new PerfilResponse(
        perfil.getDonanteId(),
        perfil.getUser(),
        perfil.getCategoria().name(),
        misionActual,
        progreso,
        objetivo,
        distancia,
        perfil.getInsignias().size(),
        perfil.totalMisionesCompletadas());
  }

  public InsigniaResponse toInsigniaResponse(Insignia insignia) {
    return new InsigniaResponse(insignia.getNombre(), insignia.getCategoria().name(), insignia.getFechaObtenida());
  }

  public MisionesResponse toMisionesResponse(PerfilDonante perfil) {
    MisionResponse actual = perfil.getMisionActual() == null
        ? null
        : toMisionResponse(perfil.getMisionActual(), perfil.getHistorial());
    List<MisionResponse> completadas = perfil.getMisionesCompletadas().stream()
        .map(mision -> toMisionResponse(mision, perfil.getHistorial()))
        .toList();
    return new MisionesResponse(actual, completadas);
  }

  public RankingResponse toRankingResponse(RankingMensual ranking) {
    List<PuestoResponse> puestos = ranking.getPuestos().stream()
        .map(this::toPuestoResponse)
        .toList();
    return new RankingResponse(ranking.getAnio(), ranking.getMes(), ranking.getFechaPublicacion(), puestos);
  }

  private MisionResponse toMisionResponse(MisionEnProgreso enProgreso, List<EventoDonacion> historial) {
    int progreso = enProgreso.getEstado() == EstadoMision.COMPLETADA
        ? enProgreso.getMision().getObjetivo()
        : enProgreso.progreso(historial);
    return new MisionResponse(
        enProgreso.getMision().getNombre(),
        enProgreso.getMision().getDescripcion(),
        enProgreso.getMision().getCategoria().name(),
        enProgreso.getEstado().name(),
        progreso,
        enProgreso.getMision().getObjetivo());
  }

  private PuestoResponse toPuestoResponse(PuestoRanking puesto) {
    return new PuestoResponse(
        puesto.getPosicion(),
        puesto.getDonanteId(),
        puesto.getDonanteUser(),
        puesto.getMisionesCompletadas());
  }
}
