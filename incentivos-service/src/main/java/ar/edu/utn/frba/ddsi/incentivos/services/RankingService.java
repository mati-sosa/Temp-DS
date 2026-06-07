package ar.edu.utn.frba.ddsi.incentivos.services;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.PerfilDonante;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.PuestoRanking;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.RankingMensual;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.PerfilDonanteRepository;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.RankingMensualRepository;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Calcula el ranking mensual de donantes más activos, por cantidad de misiones cumplidas en el mes
 * (sin importar la categoría). Destaca a los tres primeros.
 */
@Service
public class RankingService {
  private static final int TOP = 3;

  private final PerfilDonanteRepository perfilRepository;
  private final RankingMensualRepository rankingRepository;

  public RankingService(PerfilDonanteRepository perfilRepository, RankingMensualRepository rankingRepository) {
    this.perfilRepository = perfilRepository;
    this.rankingRepository = rankingRepository;
  }

  /** Genera (y persiste) el ranking del período indicado. Se puede invocar a demanda. */
  public RankingMensual generarRanking(int anio, int mes) {
    List<PerfilDonante> ordenados = perfilRepository.buscarTodos().stream()
        .sorted(Comparator.comparingInt((PerfilDonante perfil) -> perfil.misionesCompletadasEn(anio, mes))
            .reversed())
        .limit(TOP)
        .toList();

    List<PuestoRanking> puestos = new ArrayList<>();
    int posicion = 1;
    for (PerfilDonante perfil : ordenados) {
      puestos.add(new PuestoRanking(
          posicion,
          perfil.getDonanteId(),
          perfil.getUser(),
          perfil.misionesCompletadasEn(anio, mes)));
      posicion++;
    }
    return rankingRepository.guardar(new RankingMensual(anio, mes, puestos));
  }

  public Optional<RankingMensual> obtenerRanking(int anio, int mes) {
    return rankingRepository.buscar(anio, mes);
  }

  /**
   * Job calendarizado: al cierre de cada mes (1° a las 00:00) genera el ranking del mes anterior.
   * Concepto de la cátedra: ejecución asincrónica/cron en horarios de baja carga.
   */
  @Scheduled(cron = "0 0 0 1 * *", zone = "America/Argentina/Buenos_Aires")
  public void generarRankingDelMesAnterior() {
    YearMonth mesAnterior = YearMonth.now().minusMonths(1);
    generarRanking(mesAnterior.getYear(), mesAnterior.getMonthValue());
  }
}
