package ar.edu.utn.frba.ddsi.incentivos.services;

import ar.edu.utn.frba.ddsi.incentivos.dto.DashboardResponse;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.PerfilDonante;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.PuestoRanking;
import ar.edu.utn.frba.ddsi.incentivos.services.analitica.MetricasDonacionesPort;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;

/**
 * Arma la vista de analítica del donante consolidando lo que tiene Incentivos (categoría,
 * insignias, misiones, historial de eventos de donación) con lo que aporta el Servicio de
 * Donaciones a través de {@link MetricasDonacionesPort}.
 */
@Service
public class AnaliticaService {

  private final IncentivosService incentivosService;
  private final RankingService rankingService;
  private final MetricasDonacionesPort metricasDonaciones;

  public AnaliticaService(IncentivosService incentivosService, RankingService rankingService,
                          MetricasDonacionesPort metricasDonaciones) {
    this.incentivosService = incentivosService;
    this.rankingService = rankingService;
    this.metricasDonaciones = metricasDonaciones;
  }

  public DashboardResponse armarDashboard(Long donanteId) {
    PerfilDonante perfil = incentivosService.obtenerPerfil(donanteId);
    List<EventoDonacion> historial = perfil.getHistorial();

    Map<String, Integer> donacionesPorMes = new TreeMap<>();
    for (EventoDonacion evento : historial) {
      donacionesPorMes.merge(YearMonth.from(evento.getFecha()).toString(), 1, Integer::sum);
    }
    int totalBienes = historial.stream().mapToInt(EventoDonacion::getCantidadBienes).sum();

    YearMonth ahora = YearMonth.now();
    int mesActual = donacionesPorMes.getOrDefault(ahora.toString(), 0);
    int mesAnterior = donacionesPorMes.getOrDefault(ahora.minusMonths(1).toString(), 0);

    Integer posicion = rankingService.obtenerRanking(ahora.getYear(), ahora.getMonthValue())
        .flatMap(ranking -> ranking.getPuestos().stream()
            .filter(puesto -> puesto.getDonanteId().equals(donanteId))
            .map(PuestoRanking::getPosicion)
            .findFirst())
        .orElse(null);

    return new DashboardResponse(
        perfil.getDonanteId(),
        perfil.getUser(),
        perfil.getCategoria().name(),
        historial.size(),
        totalBienes,
        metricasDonaciones.organizacionesAyudadas(donanteId),
        donacionesPorMes,
        mesActual,
        mesAnterior,
        perfil.getInsignias().size(),
        perfil.totalMisionesCompletadas(),
        posicion);
  }
}
