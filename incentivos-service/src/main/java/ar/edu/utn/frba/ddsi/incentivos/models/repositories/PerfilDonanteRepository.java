package ar.edu.utn.frba.ddsi.incentivos.models.repositories;

import ar.edu.utn.frba.ddsi.incentivos.models.entities.PerfilDonante;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** Repositorio en memoria del perfil de incentivos (la persistencia real llega en la Entrega 4). */
@Repository
public class PerfilDonanteRepository {
  private final Map<Long, PerfilDonante> porDonante = new HashMap<>();

  public PerfilDonante guardar(PerfilDonante perfil) {
    porDonante.put(perfil.getDonanteId(), perfil);
    return perfil;
  }

  public Optional<PerfilDonante> buscarPorDonante(Long donanteId) {
    return Optional.ofNullable(porDonante.get(donanteId));
  }

  public boolean existePorDonante(Long donanteId) {
    return porDonante.containsKey(donanteId);
  }

  public List<PerfilDonante> buscarTodos() {
    return new ArrayList<>(porDonante.values());
  }
}
