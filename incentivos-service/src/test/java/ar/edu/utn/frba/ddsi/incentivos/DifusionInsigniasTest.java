package ar.edu.utn.frba.ddsi.incentivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.InsigniaObtenida;
import ar.edu.utn.frba.ddsi.incentivos.models.factories.CatalogoMisionesFactory;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.PerfilDonanteRepository;
import ar.edu.utn.frba.ddsi.incentivos.services.IncentivosService;
import ar.edu.utn.frba.ddsi.incentivos.services.difusion.DifusorDeInsignias;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DifusionInsigniasTest {

  /** Doble de test que registra las insignias difundidas, en lugar de pegarle a n8n. */
  static class DifusorFake implements DifusorDeInsignias {
    private final List<InsigniaObtenida> difundidas = new ArrayList<>();

    @Override
    public void difundir(InsigniaObtenida insignia) {
      difundidas.add(insignia);
    }
  }

  @Test
  void seDifundeUnaInsigniaAlCompletarUnaMision() {
    DifusorFake difusor = new DifusorFake();
    IncentivosService service =
        new IncentivosService(new PerfilDonanteRepository(), new CatalogoMisionesFactory(), difusor, (id, msg) -> { });
    service.registrarDonante(1L, "ana");

    service.registrarDonacion(1L, new EventoDonacion(LocalDate.parse("2026-05-10"), "Alimentos", 5, true));
    service.registrarDonacion(1L, new EventoDonacion(LocalDate.parse("2026-06-10"), "Alimentos", 5, true));

    assertEquals(1, difusor.difundidas.size());
    assertTrue(difusor.difundidas.get(0).getTexto().contains("ana"));
  }
}
