package ar.edu.utn.frba.ddsi.incentivos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.ddsi.incentivos.dto.DashboardResponse;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.factories.CatalogoMisionesFactory;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.PerfilDonanteRepository;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.RankingMensualRepository;
import ar.edu.utn.frba.ddsi.incentivos.services.AnaliticaService;
import ar.edu.utn.frba.ddsi.incentivos.services.IncentivosService;
import ar.edu.utn.frba.ddsi.incentivos.services.RankingService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AnaliticaServiceTest {

  @Test
  void elDashboardConsolidaDonacionesEIncentivos() {
    PerfilDonanteRepository perfilRepository = new PerfilDonanteRepository();
    IncentivosService incentivos =
        new IncentivosService(perfilRepository, new CatalogoMisionesFactory(), insignia -> { }, (id, msg) -> { });
    RankingService ranking = new RankingService(perfilRepository, new RankingMensualRepository());
    // Stub del puerto de métricas de Donaciones: 3 organizaciones ayudadas.
    AnaliticaService analitica = new AnaliticaService(incentivos, ranking, donanteId -> 3);

    incentivos.registrarDonante(1L, "ana");
    incentivos.registrarDonacion(1L, new EventoDonacion(LocalDate.parse("2026-05-10"), "Alimentos", 4, true));
    incentivos.registrarDonacion(1L, new EventoDonacion(LocalDate.parse("2026-06-10"), "Ropa", 6, true));

    DashboardResponse dashboard = analitica.armarDashboard(1L);

    assertEquals(2, dashboard.totalDonaciones());
    assertEquals(10, dashboard.totalBienesDonados());
    assertEquals(3, dashboard.organizacionesAyudadas());
    assertEquals("SOSTENEDOR", dashboard.categoria());
    assertEquals(2, dashboard.misionesCompletadas());
    assertEquals(2, dashboard.totalInsignias());
  }
}
