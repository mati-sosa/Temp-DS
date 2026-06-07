package ar.edu.utn.frba.ddsi.incentivos.services;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.Insignia;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.PerfilDonante;
import ar.edu.utn.frba.ddsi.incentivos.models.entities.misiones.Mision;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.CambioCategoria;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoDonacion;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.EventoIncentivos;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.InsigniaObtenida;
import ar.edu.utn.frba.ddsi.incentivos.models.eventos.MisionCumplida;
import ar.edu.utn.frba.ddsi.incentivos.models.factories.CatalogoMisionesFactory;
import ar.edu.utn.frba.ddsi.incentivos.models.repositories.PerfilDonanteRepository;
import ar.edu.utn.frba.ddsi.incentivos.services.difusion.DifusorDeInsignias;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class IncentivosService {
  private final PerfilDonanteRepository repositorio;
  private final CatalogoMisionesFactory catalogo;
  private final DifusorDeInsignias difusor;

  public IncentivosService(PerfilDonanteRepository repositorio, CatalogoMisionesFactory catalogo,
                           DifusorDeInsignias difusor) {
    this.repositorio = repositorio;
    this.catalogo = catalogo;
    this.difusor = difusor;
  }

  public PerfilDonante registrarDonante(Long donanteId, String user) {
    PerfilDonante perfil = new PerfilDonante(donanteId, user, catalogo.primera());
    return repositorio.guardar(perfil);
  }

  /**
   * Registra una donación e impacta en el progreso de las misiones del donante.
   * Por cada insignia obtenida dispara la difusión (n8n). Devuelve los eventos de dominio
   * generados (misión cumplida, insignia obtenida, cambio de categoría), que P3 usará para notificar.
   */
  public List<EventoIncentivos> registrarDonacion(Long donanteId, EventoDonacion evento) {
    PerfilDonante perfil = obtenerPerfil(donanteId);
    perfil.agregarDonacion(evento);

    List<EventoIncentivos> eventos = new ArrayList<>();
    while (perfil.misionActualCompletada()) {
      Mision completada = perfil.getMisionActual().getMision();
      Mision siguiente = catalogo.siguienteDe(completada);
      Insignia insignia = perfil.completarMisionActual(siguiente);

      eventos.add(new MisionCumplida(donanteId, perfil.getUser(), completada.getNombre()));

      InsigniaObtenida eventoInsignia = new InsigniaObtenida(donanteId, perfil.getUser(),
          insignia.getNombre(), insignia.getCategoria(),
          "¡" + perfil.getUser() + " obtuvo la insignia \"" + insignia.getNombre() + "\"!");
      eventos.add(eventoInsignia);
      difusor.difundir(eventoInsignia);

      if (siguiente != null && siguiente.getCategoria() != completada.getCategoria()) {
        perfil.subirCategoria(siguiente.getCategoria());
        eventos.add(new CambioCategoria(donanteId, perfil.getUser(), perfil.getCategoria()));
      }
    }
    repositorio.guardar(perfil);
    return eventos;
  }

  public PerfilDonante obtenerPerfil(Long donanteId) {
    return repositorio.buscarPorDonante(donanteId)
        .orElseThrow(() -> new NoSuchElementException(
            "No existe perfil de incentivos para el donante " + donanteId));
  }

  public int misionesCompletadasEn(Long donanteId, int anio, int mes) {
    return obtenerPerfil(donanteId).misionesCompletadasEn(anio, mes);
  }

  public List<Insignia> insignias(Long donanteId) {
    return obtenerPerfil(donanteId).getInsignias();
  }

  public List<PerfilDonante> todos() {
    return repositorio.buscarTodos();
  }
}
