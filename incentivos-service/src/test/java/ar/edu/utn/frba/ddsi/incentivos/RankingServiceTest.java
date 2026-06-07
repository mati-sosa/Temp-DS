package ar.edu.utn.frba.ddsi.incentivos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.ranking.RankingMensual;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.factories.CatalogoMisionesFactory;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.PerfilDonanteRepository;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.RankingMensualRepository;
import ar.edu.utn.frba.ddsi.incentivos.services.IncentivosService;
import ar.edu.utn.frba.ddsi.incentivos.services.RankingService;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RankingServiceTest {

  private PerfilDonanteRepository perfilRepository;
  private IncentivosService incentivos;
  private RankingService ranking;

  @BeforeEach
  void setUp() {
    perfilRepository = new PerfilDonanteRepository();
    incentivos = new IncentivosService(perfilRepository, new CatalogoMisionesFactory(), insignia -> { }, (id, msg) -> { });
    ranking = new RankingService(perfilRepository, new RankingMensualRepository());
  }

  @Test
  void elPrimeroEsElDonanteConMasMisionesCumplidasEnElMes() {
    // Donante 1: dos donaciones de distinta categoría en meses consecutivos -> completa 2 misiones.
    incentivos.registrarDonante(1L, "ana");
    incentivos.registrarDonacion(1L, new EventoDonacion(LocalDate.parse("2026-05-10"), "Alimentos", 5, true));
    incentivos.registrarDonacion(1L, new EventoDonacion(LocalDate.parse("2026-06-10"), "Ropa", 5, true));

    // Donante 2: una sola donación -> no completa ninguna misión.
    incentivos.registrarDonante(2L, "beto");
    incentivos.registrarDonacion(2L, new EventoDonacion(LocalDate.parse("2026-06-10"), "Alimentos", 5, true));

    YearMonth ahora = YearMonth.now();
    RankingMensual resultado = ranking.generarRanking(ahora.getYear(), ahora.getMonthValue());

    assertEquals(2, resultado.getPuestos().size());
    assertEquals(1, resultado.getPuestos().get(0).getPosicion());
    assertEquals(Long.valueOf(1L), resultado.getPuestos().get(0).getDonanteId());
    assertEquals(2, resultado.getPuestos().get(0).getMisionesCompletadas());
  }
}
