package ar.edu.utn.frba.ddsi.incentivos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Categoria;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.PerfilDonante;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoIncentivos;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.InsigniaObtenida;
import ar.edu.utn.frba.ddsi.incentivos.models.factories.CatalogoMisionesFactory;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.PerfilDonanteRepository;
import ar.edu.utn.frba.ddsi.incentivos.services.IncentivosService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncentivosDominioTest {

  private static final Long DONANTE = 1L;
  private IncentivosService service;

  @BeforeEach
  void setUp() {
    // Difusor no-op: este test verifica el dominio, no la difusión a n8n.
    service = new IncentivosService(new PerfilDonanteRepository(), new CatalogoMisionesFactory(), insignia -> { }, (id, msg) -> { });
    service.registrarDonante(DONANTE, "ana_donante");
  }

  @Test
  void perfilNuevoArrancaEnColaboradorSinInsignias() {
    PerfilDonante perfil = service.obtenerPerfil(DONANTE);
    assertEquals(Categoria.COLABORADOR, perfil.getCategoria());
    assertTrue(perfil.getInsignias().isEmpty());
    assertEquals("Racha x2", perfil.getMisionActual().getMision().getNombre());
  }

  @Test
  void unaSolaDonacionNoCompletaLaRacha() {
    List<EventoIncentivos> eventos =
        service.registrarDonacion(DONANTE, donacion("2026-05-10", "Alimentos", 5, true));

    assertTrue(eventos.isEmpty());
    PerfilDonante perfil = service.obtenerPerfil(DONANTE);
    assertEquals("Racha x2", perfil.getMisionActual().getMision().getNombre());
    assertTrue(perfil.getInsignias().isEmpty());
  }

  @Test
  void completarRachaDeDosMesesOtorgaInsigniaYEmiteEventos() {
    service.registrarDonacion(DONANTE, donacion("2026-05-10", "Alimentos", 5, true));
    List<EventoIncentivos> eventos =
        service.registrarDonacion(DONANTE, donacion("2026-06-10", "Alimentos", 5, true));

    assertFalse(eventos.isEmpty());
    assertTrue(eventos.stream().anyMatch(evento -> evento instanceof InsigniaObtenida));
    assertEquals(1, service.obtenerPerfil(DONANTE).getInsignias().size());
  }

  @Test
  void completarLasDosMisionesDeColaboradorAsciendeASostenedor() {
    service.registrarDonacion(DONANTE, donacion("2026-05-10", "Alimentos", 5, true));
    service.registrarDonacion(DONANTE, donacion("2026-06-10", "Ropa", 5, true));

    PerfilDonante perfil = service.obtenerPerfil(DONANTE);
    assertEquals(Categoria.SOSTENEDOR, perfil.getCategoria());
    assertEquals(2, perfil.getInsignias().size());
    assertEquals("Racha x6", perfil.getMisionActual().getMision().getNombre());
  }

  @Test
  void cuentaMisionesCompletadasEnElPeriodoActual() {
    service.registrarDonacion(DONANTE, donacion("2026-05-10", "Alimentos", 5, true));
    service.registrarDonacion(DONANTE, donacion("2026-06-10", "Ropa", 5, true));

    YearMonth ahora = YearMonth.now();
    assertEquals(2, service.misionesCompletadasEn(DONANTE, ahora.getYear(), ahora.getMonthValue()));
  }

  private EventoDonacion donacion(String fecha, String categoria, int bienes, boolean exitosa) {
    return new EventoDonacion(LocalDate.parse(fecha), categoria, bienes, exitosa);
  }
}
